package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jdbc.JdbcUtil;
import article.model.Article;

public class ArticleDao {

	// 1. 게시글 삽입 (성공)
	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("insert into jsp_crud "
					+ "(article_id, article_title, article_content) " + "values (idx_seq.nextVal, ?, ?)");
			pstmt.setString(1, article.getTitle());
			pstmt.setString(2, article.getContent());
			
			pstmt.executeUpdate();

			return new Article(article.getTitle(), article.getContent());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	// 2. 게시글 조회 (가장 최근 게시물) (성공)
	public Article selectById(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from jsp_crud where article_id = ?");
			pstmt.setInt(1, getSeq(conn));
			rs = pstmt.executeQuery();
			
			Article article = null;
			
			if(rs.next()) {
				article = convertArticle(rs);
			}
			return article;
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getString("article_title"),
				rs.getString("article_content"));
	}
	
	// 3. 게시글을 수정 (가장 최근 게시물) (성공)
	public int update(Connection conn, String title, String content) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("update jsp_crud set article_title = ?, article_content = ? " + "where article_id = ?")) {
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, getSeq(conn));
			return pstmt.executeUpdate();
		}
	}
	
	// 4. 게시글을 삭제 (가장 최근 게시물) (아마 성공?)
	public int delete(Connection conn) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from jsp_crud where article_id = ?")) {
			pstmt.setInt(1, getSeq(conn));
			return pstmt.executeUpdate();
		}
	}
	
	// 5. 가장 최근 시퀀스 얻는 메소드
	public int getSeq(Connection conn) throws SQLException {
		
		ResultSet rs = null;
		int n = 0;
		try (Statement stmt = conn.createStatement()){
			
			rs = stmt.executeQuery("select max(article_id) from jsp_crud");
			
			if(rs.next()) {
				n = rs.getInt(1);
			}
			return n;
		} finally {
			JdbcUtil.close(rs);
		}
	}
}
