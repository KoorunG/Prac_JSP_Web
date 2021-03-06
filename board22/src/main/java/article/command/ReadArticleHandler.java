package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleCotentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readService = new ReadArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String noVal = request.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		
		try {
			
			ArticleData articleData = readService.getArticle(articleNum, true);
			
			// request의 articleData 속성에 게시글을 저장한다!
			request.setAttribute("articleData", articleData);
			return "/WEB-INF/view/readArticle.jsp";
			
			// 예외가 발생한다면
		} catch (ArticleNotFoundException | ArticleCotentNotFoundException e) {
			
			// 로그 메세지를 기록하고
			request.getServletContext().log("no article", e);
			
			// 404 NOT_FOUND 에러를 전송
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
	}
	
}
