package article.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Article;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import mvc.command.CommandHandler;


public class ModifyArticleHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/modifyForm.jsp";
	
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
			Article article = readArticleService.getArticle();	
			
			// 인증 처리된 유저의 정보와, articleData를 이용하여 ModifyRequest 객체를 생성하여 request의 modReq 속성에 저장함
			ModifyRequest modReq = new ModifyRequest(article.getTitle(), article.getContent());
																												
			request.setAttribute("modReq", modReq);
			return FORM_VIEW;
			
			// 게시글이 존재하지 않으면 예외처리 후 404 Not Found 응답코드를 전송
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	// POST일 경우 실행되는 메소드
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 요청파라미터와 로그인한 사용자 정보를 이용하여 ModifyRequest 객체 생성
		ModifyRequest modReq = new ModifyRequest(request.getParameter("title"), request.getParameter("content"));
		
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
