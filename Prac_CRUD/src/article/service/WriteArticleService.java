package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteArticleService {

	private ArticleDao articleDao = new ArticleDao();
	
	public void write(WriteRequest req) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			conn.setAutoCommit(false); 
			
			Article article = toArticle(conn, req);
			
			Article savedArticle = articleDao.insert(conn, article);
			
			if(savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}
			
			conn.commit();
			
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private Article toArticle(Connection conn, WriteRequest req) throws SQLException {
		return new Article(req.getTitle(), req.getContent());
	}
}
