package article.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

// GET : 수정할 게시글 데이터를 읽어와 폼에 보여줌
// POST : 전송한 요청 파라미터를 이용하여 게시글을 수정함 파라미터값이 잘못된 경우 전송한 데이터를 이용해서 폼을 다시 보여줌
// 앞의 핸들러는 GET 방식에 대해 폼을 출력할 때 폼에 데이터를 채우지 않았는데
// 지금의 경우 GET 방식으로 폼을 요청할 때 수정할 데이터 내용을 폼에 채워 보여줘야함

public class ModifyArticleHandler implements CommandHandler {

	// 뷰 파일 경로 설정
	private static final String FORM_VIEW = "/WEB-INF/view/modifyForm.jsp";
	
	// 게시글을 읽어온 뒤 내용을 수정해야 하므로 ReadArticleService 객체 또한 필요함
	private ReadArticleService readArticleService = new ReadArticleService();
	private ModifyArticleService modifyService = new ModifyArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			// 1. GET일 경우
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
			// 2. POST일 경우
		} else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
			// 3. 그 이외일 경우 405 Not Allowed 응답코드 전송
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	// GET일 경우 실행되는 메소드
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String noVal = request.getParameter("no");
			int no = Integer.parseInt(noVal);
			ArticleData articleData = readArticleService.getArticle(no, false);
			User authUser = (User)request.getSession().getAttribute("authUser");
			if(!canModify(authUser, articleData)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			// 인증 처리된 유저의 정보와, articleData를 이용하여 ModifyRequest 객체를 생성하여 request의 modReq 속성에 저장함
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, articleData.getArticle().getTitle(), articleData.getContent());
																												
			request.setAttribute("modReq", modReq);
			return FORM_VIEW;
			
			// 게시글이 존재하지 않으면 예외처리 후 404 Not Found 응답코드를 전송
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	// 앞서 Page 668 47행 ModifyArticleService의 canModify() 메서드 비교 바람
	private boolean canModify(User authUser, ArticleData articleData) {
		String writerId = articleData.getArticle().getWriter().getId();
		return authUser.getId().equals(writerId);
	}

	// POST일 경우 실행되는 메소드
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User authUser = (User)request.getSession().getAttribute("authUser");
		String noVal = request.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		// 요청파라미터와 로그인한 사용자 정보를 이용하여 ModifyRequest 객체 생성
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, request.getParameter("title"), request.getParameter("content"));
		
		// ModifyRequest 객체를 request의 modReq 속성에 저장함
		request.setAttribute("modReq", modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		modReq.validate(errors);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			modifyService.modify(modReq);
			return "/WEB-INF/view/modifySuccess.jsp";
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
	
	
}
