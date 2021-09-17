package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.ChangePasswordService;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import mvc.command.CommandHandler;

public class ChangePasswordHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/changePwdForm.jsp";
	private ChangePasswordService changePwdSvc = new ChangePasswordService();

	// 요청 방식이 GET(또는 get)이면 processForm()을 실행하고,
	// 요청 방식이 POST(또는 post)이면 processSubmit()을 실행한다.
	// 참고로, equls() 메서드는 문자열 비교 시 대소문자를 구분해서,
	// 문자열이 같은 경우 true 리턴하고, 다른 경우 false 리턴함.
	// 하지만, equalsIgnoreCase() 메서드는 문자열 비교 시 대소문자를
	// 구분하지 않음. 즉, 대소문자 구분 없이 문자열만 같다면 true 리턴하고,
	// 다른 경우 false 리턴함.
	// GET 또는 POST가 아니면 405 응답 코드를 전송한다.
	// 참고로, static int SC_METHOD_NOT_ALLOWED
	// 405 응답 코드 전송 (허용되지 않는 메소드 응답)
	// 지정된 메서드가 식별 된 리소스에 대해 허용되지 않음을 나타내는
	// 상태 코드 (405)를 의미함.

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

	}

	// 폼 화면으로 이동
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}
	
	// 폼 전송 처리
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("authUser");
		
		// 에러정보를 담을 맵 객체를 생성하고 attribute에 추가
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);

		// 파라미터에서 curPwd, newPwd를 얻어와 같은 이름의 필드에 저장
		String curPwd = request.getParameter("curPwd");
		String newPwd = request.getParameter("newPwd");

		if(curPwd.equals(newPwd)) {
			errors.put("samePwd", Boolean.TRUE);
		}
		
		// 만일 현재 패스워드가 null 또는 비어있다면 에러에 추가
		if (curPwd == null || curPwd.isEmpty()) {
			errors.put("curPwd", Boolean.TRUE);
		}

		// 만일 새 패스워드가 null 또는 비어있다면 에러에 추가
		if (newPwd == null || newPwd.isEmpty()) {
			errors.put("newPwd", Boolean.TRUE);
		}

		// 에러가 비어있지 않다면(즉, 에러가 존재한다면)
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		// changePasswordService를 호출하여 changePassword 메소드를 실행 -> 비번을 바꾼 뒤,
		// 지정한 뷰 파일로 보낸다
		try {
			changePwdSvc.changePassword(user.getId(), curPwd, newPwd);
			return "/WEB-INF/view/changePwdSuccess.jsp";
			
			// curPwd가 올바르지 않아 InvalidPasswordException 익셉션이 발생하면
			// "badCurPwd"의 이름으로 errors에 추가하고 FORM_VIEW로 보낸다
		} catch (InvalidPasswordException e) {
			errors.put("badCurPwd", Boolean.TRUE);
			return FORM_VIEW;
			
			// 암호를 변경할 회원아이디가 존재하지 않아 MemberNotFoundException 익셉션이 발생하면
			// 400 BAD_REQUEST 응답코드를 보내고 null을 리턴한다
		} catch (MemberNotFoundException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}

}
