package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

// CommandHandler 구현
public class LoginHandler implements CommandHandler {

	// loginForm.jsp 뷰 파일 경로를 상수로 설정
	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	
	// LoginService 객체를 생성하여 로그인에 이용함
	private LoginService loginService = new LoginService();
	
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request,response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return null;
	}

	// 1. GET 요청이 왔을 시 바로 로그인 폼으로 이동
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}
	
	// 2. POST 요청이 왔을 파라미터로 넘어온 id와 password를 request.getParameter 메소드를 이용해 꺼낸 뒤 변수에 저장
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = trim(request.getParameter("id"));
		String password = trim(request.getParameter("password"));
		
		// errors 맵 생성 후, Attribute로 등록함
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		// 2-1. 아이디가 비어있을 경우
		if(id == null || id.isEmpty()) {
			// "id"를 키 값으로 errors에 등록함
			errors.put("id", Boolean.TRUE);
		}
		
		// 2-2. 패스워드가 비어있을 경우
		if(password == null || password.isEmpty()) {
			// "password"를 키 값으로 errors에 등록함
			errors.put("password", Boolean.TRUE);
		}
		
		// errors가 비어있지 않다면(즉, 에러가 있다면) 로그인 폼으로 보냄
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			// LoginService 객체의 login 메소드를 이용하여 User 객체를 생성한 뒤 user에 담음
			User user = loginService.login(id, password);
			// 3. 요청에서 세션값을 얻어와 user를 "authUser"으로 Attribute 등록함
			request.getSession().setAttribute("authUser", user);
			// 그 뒤, 요청의 컨텍스트 경로(여기서는 /board22) + /index.jsp 의 경로로 '리다이렉트' 시킴! (로그인 성공)
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return null;
		} catch (LoginFailException e) {
			// 예외가 발생하였다면, errors에 "idOrPwNotMatch"의 이름으로 등록을 하고
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			// 로그인 폼으로 보내버림
			return FORM_VIEW;
		}
	}

	// 빈칸을 커버해주는 메소드 trim
	private String trim(String str) {
		return str == null ? null : str.trim();
	}

	
	
}
