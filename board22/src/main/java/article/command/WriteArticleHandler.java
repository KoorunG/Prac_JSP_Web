package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;

// 웹 요청을 처리할 핸들러 구현
// GET 방식과 POST 방식 요청을 별도의 메소드에서 처리한다

public class WriteArticleHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/newArticleForm.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET") ) {		// equalsEgnoreCase => 같은 값을 확인할 때 대소문자를 구분하지 않는다는 메소드!
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			
			// static int	SC_METHOD_NOT_ALLOWED
	        // 405 응답 코드 전송 (허용되지 않는 메소드 응답)
	        // 지정된 메서드가 식별 된 리소스에 대해 허용되지 않음을 나타내는
			// 상태 코드 (405) 입니다.

			return null;
		}
	}

	// GET일때
	// FORM_VIEW(newArticleForm.jsp) 로 보냄
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}
	
	// POST일때
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		
		// 에러 맵 생성
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		// 세션에서 로그인한 사용자 정보를 구함
		// (로그인하여 세션이 유지될 경우 "authUser"이라는 Attribute를 추가시켰었는데 그걸 가져온 것... 
		User user = (User)request.getSession(false).getAttribute("authUser");
		
		// createWriteRequest 메소드를 이용하여 생성한 객체 정보를 writeReq에 담음
		WriteRequest writeReq = createWriteRequest(user,request);
		
		// writeReq 검증!
		writeReq.validate(errors);
		
		// 1. 에러가 있다면
		// FORM_VIEW(newArticleForm.jsp) 로 보냄
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		// 2. 에러가 없다면
		// writeService의 write 메소드를 호출하여 리턴값을 newArticleNo 변수에 담고
		// Attribute를 설정한다
		int newArticleNo = writeService.write(writeReq);
		request.setAttribute("newArticleNo", newArticleNo);
		
		// 그리고 newArticleSuccess.jsp 로 보낸다!
		return "/WEB-INF/view/newArticleSuccess.jsp";
	}

	// HttpServletRequest와 User를 이용하여 WriteRequest 객체를 생성하는 메소드
	private WriteRequest createWriteRequest(User user, HttpServletRequest request) {
		return new WriteRequest(new Writer(user.getId(), user.getName()),
								request.getParameter("title"),
								request.getParameter("content"));
	}
	
	/**
	 * 
	 * public WriteRequest(Writer writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
	 */

	
}
