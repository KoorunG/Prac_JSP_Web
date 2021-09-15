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

public class ControllerUsingFile extends HttpServlet{
	
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
	
	
	// 설정 파일로부터 매핑 정보를 읽어와서 Properties 객체에 저장한다.
	// Properties는 목록(이름, 값)을 갖는 클래스로서,
	// 프로퍼티 커맨드 이름으로 사용하고 값을 클래스 이름으로 사용한다.

	
	// 1. init()으로 서블릿이 시작할 때 초기화
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
		
		// 2. 입력한 command로 handler 객체를 얻어옴
		
		String command = request.getParameter("cmd");
		// 클라이언트가 요청한 명령어를 구함
		// 예제에서는 cmd 파라미터값을 명령어로 사용함
		
		CommandHandler handler = commandHandlerMap.get(command);
		// cmd=value로 얻어온 value를 키 값으로 핸들러 객체를 꺼내오는 과정
		
		if(handler == null) {
			handler = new NullHandler();
		}
		// 만약 핸들러가 없다면(입력한 파라미터에 해당하는 핸들러가 없다면
		// 핸들러를 NullHandler으로 지정함
		
		
		String viewPage = null;
		
		// 3. viewPage에 Path 저장
		
		try {
			viewPage = handler.process(request, response);
			
			// 핸들러를 이용하여 요청을 처리하고
			// 핸들러를 처리한 결과를 viewPage로 지정함 (리턴타입이 String이며 viewPage의 주소가 적혀있음)
		} catch (Exception e) {
			throw new ServletException(e);
			// 예외가 발생한다면 ServletException으로 예외처리
		}
		
		// 4. 포워딩
		
		if(viewPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		
		// viewPage값이 null이 아닐 경우, dispatcher을 이용하여 해당 jsp파일로 포워딩함 (path가 viewPage임)
	}
		
}
