package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteArticleService {
	
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public void delete(DeleteRequest delReq) {
		Connection conn = null;
		
		try {
			// 커넥션 연결
			conn = ConnectionProvider.getConnection();
			
			// 트랜잭션 시작
			conn.setAutoCommit(false);
			
			// DAO에서 Article 객체를 얻어옴
			Article article = articleDao.selectById(conn, delReq.getArticleNumber());
			
			// 해당 게시글이 존재하지 않으면 예외처리
			if(article == null) {
				throw new ArticleNotFoundException();
			}
			
			// 해당 게시글의 글쓴이가 아니라면 예외처리
			if(!canDelete(delReq.getUserId(), article)) {
				throw new PermissionDeniedException();
			}
			
			// delete 실행
			articleDao.delete(conn, delReq.getArticleNumber());
			contentDao.delete(conn, delReq.getArticleNumber());
			
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
	
	private boolean canDelete(String modifyingUserId, Article article) {
		return article.getWriter().getId().equals(modifyingUserId);
	}
}
