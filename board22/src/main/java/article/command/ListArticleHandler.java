package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler{

	// 서비스 로직 호출
	private ListArticleService listService = new ListArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// pageNo 파라미터 값을 이용하여 읽어올 페이지 번호를 구함
		String pageNoVal = request.getParameter("pageNo");
		
		int pageNo = 1;
		if(pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		// 지정한 페이지 번호에 해당하는 게시글 데이터를 구하여 articlePage에 저장함
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		
		// Attribute를 지정하고
		request.setAttribute("articlePage", articlePage);
		
		// listArticle.jsp로 리턴한다
		return "/WEB-INF/view/listArticle.jsp";
	}

	
}
