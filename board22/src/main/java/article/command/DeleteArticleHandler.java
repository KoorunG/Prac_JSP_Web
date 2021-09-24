package article.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.DeleteArticleService;
import article.service.DeleteRequest;
import article.service.PermissionDeniedException;
//import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/deleteForm.jsp";
//	private ReadArticleService readArticleService = new ReadArticleService();
	private DeleteArticleService deleteService = new DeleteArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String noVal = request.getParameter("no");
			int no = Integer.parseInt(noVal);
//			ArticleData articleData = readArticleService.getArticle(no, false);
			User authUser = (User)request.getSession().getAttribute("authUser");
//			if(!canModify(authUser, articleData)) {
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
//				return null;
//			}
			
			DeleteRequest delReq = new DeleteRequest(authUser.getId(), no);
																											
			request.setAttribute("delReq", delReq);
			return FORM_VIEW;
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

//	private boolean canModify(User authUser, ArticleData articleData) {
//		String writerId = articleData.getArticle().getWriter().getId();
//		return authUser.getId().equals(writerId);
//	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User authUser = (User)request.getSession().getAttribute("authUser");
		String noVal = request.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		DeleteRequest delReq = new DeleteRequest(authUser.getId(), no);
		request.setAttribute("delReq", delReq);
		
		try {
			deleteService.delete(delReq);
			return "/WEB-INF/view/deleteSuccess.jsp";
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}

	
}
