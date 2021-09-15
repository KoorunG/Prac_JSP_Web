package mvc.simple;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Controller : 웹브라우저가 전송한 type 파라미터에 따라서 다른 결과값을 생성해서 뷰에 전달하는 역할

public class SimpleController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 요청 파악
		String type = request.getParameter("type");
		
		Object resultObject = null;
		
		// 요청 기능 수행 (별도의 모델을 설정하지 않았으므로 컨트롤러에서 직접 요청을 처리한 것)
		if(type == null || type.equals("greeting")) {
			resultObject = "안녕하세요";
		} else if (type.equals("date")) {
			resultObject = new java.util.Date();
		} else {
			resultObject = "Invalid Type";
		}
		
		// request.setAttribute로 결과 저장
		request.setAttribute("result", resultObject);
		
		// RequestDispatcher으로 적절한 뷰로 포워딩
		RequestDispatcher dispatcher = request.getRequestDispatcher("/simpleView.jsp");
		dispatcher.forward(request, response);
	}
}
