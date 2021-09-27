package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteArticleService {
	
	private ArticleDao articleDao = new ArticleDao();
	
	public void delete() {
		Connection conn = null;
		
		try {
			// 커넥션 연결
			conn = ConnectionProvider.getConnection();
			
			// 트랜잭션 시작
			conn.setAutoCommit(false);
			
			// DAO에서 Article 객체를 얻어옴
			Article article = articleDao.selectById(conn);
			
			// 해당 게시글이 존재하지 않으면 예외처리
			if(article == null) {
				throw new ArticleNotFoundException();
			}
			
			// delete 실행
			articleDao.delete(conn);
			
			// 트랜잭션 종료
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (Exception e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
