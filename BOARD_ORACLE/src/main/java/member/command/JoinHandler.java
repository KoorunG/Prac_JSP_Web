package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

// JoinHandler :
// GET 방식으로 요청이 오면 폼을 보여주는 뷰인 joinForm.jsp를 리턴함
// POST 방식으로 요청이 오면 회원가입 처리를 하고 결과를 보여주는 뷰를 리턴함
// 입력데이터가 잘못된 경우 다시 joinForm.jsp를 뷰로 리턴함
// 회원가입에 성공한 경우 joinSuccess.jsp를 뷰로 리턴함

public class JoinHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";
	private JoinService joinService = new JoinService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// GET요청이 왔을 시, processForm을 수행함 (회원가입 폼)
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
			
		// POST요청이 왔을 시, processSubmit을 수행함 (회원가입 데이터 전송)	
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
			
		// 그 이외의 요청이 오면, 405 NOT_ALLOWED 응답코드를 발송함 (허용되지 않은 요청)
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); 
			// HttpServletResponse.SC_METHOD_NOT_ALLOWED = 405
			return null;
		}
	}
	
	// 1. 회원가입을 처리하는 메소드 processSubmit (폼 전송 처리)
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		
		// 폼에 입력한 데이터를 이용하여 JoinRequest 객체를 생성함
		JoinRequest joinRequest = new JoinRequest();
		joinRequest.setId(request.getParameter("id"));
		joinRequest.setName(request.getParameter("name"));
		joinRequest.setPassword(request.getParameter("password"));
		joinRequest.setConfirmPassword(request.getParameter("confirmPassword"));
		
		
		// 에러를 담당하는 errors 맵을 만들고 Attribute에 추가함
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);			// 프론트단과 연결되는 attribute 설정...
		
		// 그리고 validate를 이용하여 joinRequest를 검증함
		joinRequest.validate(errors);
		
		// 에러가 존재한다면 FORM_VIEW로 보냄
		// joinReq는 폼에 입력한 데이터를 저장하고 있으므로 폼에 입력한 데이터가 올바르지 않으면 FORM_VIEW를 리턴함
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		// joinService의 로직을 실행하는데 예외가 발생하지 않는다면 로그인 성공 페이지를 리턴하며
		try {
			joinService.join(joinRequest);
			return "/WEB-INF/view/joinSuccess.jsp";
			
			// 역시 예외가 발생한다면(중복 오류) FORM_VIEW로 보냄
		} catch (DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
	
	// 폼을 보여주는 path를 리턴함
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		
		return FORM_VIEW;
	}
	
	
}
