package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyArticleService {
	
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			// DAO에서 글 번호를 이용하여 게시글을 읽어온다
			Article article = articleDao.selectById(conn, modReq.getArticleNumber());
			if(article == null) {
				throw new ArticleNotFoundException();
			}
			
			// 게시글의 작성자만이 수정할 수 있다
			if(!canModify(modReq.getUserId(), article)) {
				throw new PermissionDeniedException();
			}
			
			// 게시글의 상세 정보 업데이트
			articleDao.update(conn, modReq.getArticleNumber(), modReq.getTitle());
			// 게시글의 내용 업데이트
			contentDao.update(conn, modReq.getArticleNumber(), modReq.getContent());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private boolean canModify(String modifyingUserId, Article article) {
		return article.getWriter().getId().equals(modifyingUserId);
	}
}
