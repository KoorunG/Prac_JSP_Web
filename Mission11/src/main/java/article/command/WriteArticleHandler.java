package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.WriteArticleService;
import article.service.WriteRequest;
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
		
		WriteRequest writeReq = createWriteRequest(request);
		
		// writeReq 검증!
		writeReq.validate(errors);
		
		// 1. 에러가 있다면
		// FORM_VIEW(newArticleForm.jsp) 로 보냄
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		writeService.write(writeReq);
		// 그리고 newArticleSuccess.jsp 로 보낸다!
		return "/WEB-INF/view/newArticleSuccess.jsp";
	}

	// HttpServletRequest와 User를 이용하여 WriteRequest 객체를 생성하는 메소드
	private WriteRequest createWriteRequest(HttpServletRequest request) {
		return new WriteRequest(request.getParameter("article_title"),request.getParameter("article_content"));
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
