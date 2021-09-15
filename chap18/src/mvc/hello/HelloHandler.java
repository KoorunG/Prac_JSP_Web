package mvc.hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;

public class HelloHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("hello", "안녕하세요");
		// request의 Attribute => 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시저장소 기능임
		// 저장 : request.setAttribute(name, value)
		// 조회 : request.getAttribute(name)
		
		// Model에서 HttpServletRequest 객체를 사용하며, request의 내부저장소인 Attribute를 이용하여 데이터를 보관하고 조회하는 역할을 한다
		// 사실상 이 Handler 클래스가 Model이라고 생각하면 됨
		
		return "/WEB-INF/view/hello.jsp";
	}
	
}
