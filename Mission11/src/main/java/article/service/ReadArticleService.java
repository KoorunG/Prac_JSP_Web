package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.connection.ConnectionProvider;

// 게시글 읽기 기능을 제공하는 ReadArticleService 클래스 구현
public class ReadArticleService {

		private ArticleDao articleDao = new ArticleDao();
		
		public Article getArticle() {
			try (Connection conn = ConnectionProvider.getConnection()) {
				
				Article article = articleDao.selectById(conn);
				if(article == null) {
					throw new ArticleNotFoundException();
				}
				return article;
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
}
