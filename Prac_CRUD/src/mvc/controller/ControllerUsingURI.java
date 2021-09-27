package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import mvc.command.NullHandler;

public class ControllerUsingURI extends HttpServlet{
	
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
	
	
	// 설정 파일로부터 매핑 정보를 읽어와서 Properties 객체에 저장한다.
	// Properties는 목록(이름, 값)을 갖는 클래스로서,
	// 프로퍼티 커맨드 이름으로 사용하고 값을 클래스 이름으로 사용한다.
	
	@Override
	public void init() throws ServletException {
		String configFile = getInitParameter("configFile");
		Properties prop = new Properties();
		String configFilePath = getServletContext().getRealPath(configFile);
		
		
		try(FileReader fr = new FileReader(configFilePath)) {
			prop.load(fr);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		// Properties에 저장된 각 프로퍼티 
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();
			
			// command에 해당하는 handlerClassName을 Properties에서 얻는다
			String handlerClassName = prop.getProperty(command);
			try {
				
				// handlerClassName 을 이용하여 Class 객체를 구함 => handlerClass
				Class<?> handlerClass = Class.forName(handlerClassName);
				CommandHandler handlerInstance = (CommandHandler)handlerClass.newInstance();
				
				// commandHandlerMap에 매핑 정보를 저장함 (key = 커맨드, value = 핸들러 객체)
				commandHandlerMap.put(command, handlerInstance);
			} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
		}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI();
		if(command.indexOf(request.getContextPath()) == 0) {
			command = command.substring(request.getContextPath().length());
		}
		
		// 컨텍스트의 길이만큼 잘라서 command로 지정함
		// 프로퍼티를 보면 /hello.do 자체에 핸들러를 매핑한 것을 볼 수 있음
		
		CommandHandler handler = commandHandlerMap.get(command);
		if(handler == null) {
			handler = new NullHandler();
		}
		
		String viewPage = null;
		
		try {
			viewPage = handler.process(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		if(viewPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}
		
}
