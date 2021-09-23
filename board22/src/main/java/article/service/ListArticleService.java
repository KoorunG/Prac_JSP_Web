package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.connection.ConnectionProvider;

public class ListArticleService {
	
	private ArticleDao articleDao = new ArticleDao();
	private int size = 10;
	
	public ArticlePage getArticlePage(int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			
			int total = articleDao.selectCount(conn);
			
			// pageNum에 해당하는 게시글 목록을 구한다
			// articleDao.select() 메소드의 두번째 파라미터인 (pageNum -1) * size는 조회할 레코드의 시작행이다.
			// 시작행은 0번 기준으로 (pageNum -1) * size를 시작 행 번호로 사용한다.
			// ex) 3페이지를 요청하면 (3-1) * 10인 20을 시작 행 번호로 사용
			// 3페이지는 21번째 레코드부터 10개의 레코드를 구하므로
			// 시작 행 번호는 20이다
			
			List<Article> content = articleDao.select(conn, (pageNum -1) * size , size);
			
			// ArticlePage 리턴
			return new ArticlePage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
