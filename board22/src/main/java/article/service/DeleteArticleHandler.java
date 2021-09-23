package article.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/deleteForm.jsp";
	private ReadArticleService readArticleService = new ReadArticleService();
	private DeleteArticleService deleteService = new DeleteArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		return null;
	}

	
}
