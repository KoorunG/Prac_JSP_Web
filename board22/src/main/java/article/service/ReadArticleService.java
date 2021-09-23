package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.connection.ConnectionProvider;

// 게시글 읽기 기능을 제공하는 ReadArticleService 클래스 구현
public class ReadArticleService {

		private ArticleDao articleDao = new ArticleDao();
		private ArticleContentDao contentDao = new ArticleContentDao();
		
		public ArticleData getArticle(int articleNum, boolean increaseReadCount) {
			try (Connection conn = ConnectionProvider.getConnection()) {
				
				// articleDao에서 지정한 번호의 Article 객체를 구한다
				Article article = articleDao.selectById(conn, articleNum);
				
				// 만약 Article 객체가 존재하지 않는다면 예외처리
				if(article == null) {
					throw new ArticleNotFoundException();
				}
				ArticleContent content = contentDao.selectById(conn, articleNum);
				
				// 만약 ArticleContent 객체가 존재하지 않는다면 예외처리
				if(content == null) {
					throw new ArticleCotentNotFoundException();
				}
				
				// increaseReadCount = true 일 경우 
				// articleDao의 increaseReadCount() 메소드를 호출하여 조회수를 1 올린다
				if(increaseReadCount) {
					articleDao.increaseReadCount(conn, articleNum);
				}
				
				// ArticleData 객체를 리턴한다
				return new ArticleData(article, content);
				
				// 도중에 예외가 발생한다면 RuntimeException 예외처리를 한다
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
}
