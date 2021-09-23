package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {

	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 띄어쓰기를 주의해야 한다
			pstmt = conn.prepareStatement("insert into article "
					+ "(writer_id, writer_name, title, regdate, moddate, read_cnt) " + "values (?, ?, ?, ?, ?, 0)");
			pstmt.setString(1, article.getWriter().getId());
			pstmt.setString(2, article.getWriter().getName());
			pstmt.setString(3, article.getTitle());
			pstmt.setTimestamp(4, toTimeStamp(article.getRegDate()));
			pstmt.setTimestamp(5, toTimeStamp(article.getModifiedDate()));
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from article");
				if (rs.next()) {
					Integer newNum = rs.getInt(1);
					return new Article(newNum, article.getWriter(), article.getTitle(), article.getRegDate(),
							article.getModifiedDate(), 0);
				}
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}

	}

	private Timestamp toTimeStamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	// 게시글 목록을 표현하려면 다음의 두 데이터를 가져올 수 있어야 한다.
	// 1) 페이지 개수를 구하기 위한 전체 게시글 개수
	// 2) 지정한 행 번호에 해당하는 게시글 목록
	// 이 두 데이터를 가져오는데 필요한 메소드를 ArticleDao에 추가함
	
	// 먼저 전체 게시글 개수를 구하기 위한 "select count(*).. " 쿼리문을 날리는 selectCount() 메소드 구현
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try { 
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	// 지정한 범위의 게시글을 읽어오기 위한 select() 메소드 구현
	public List<Article> select(Connection conn, int startRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article order by article_no desc limit ?, ?");
			// MySQL의 limit의 
			// 첫번째 인수 => 읽어올 레코드의 첫 번째 행 번호 (0부터 시작함)
			// 두번째 인수 => 읽어올 레코드 개수
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while(rs.next()) {
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	// ResultSet에서 데이터를 가져와 Article 객체를 생성하여 반환해주는 메소드
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"),
				new Writer(rs.getString("writer_id"),
						rs.getString("writer_name")),
				rs.getString("title"),
				toDate(rs.getTimestamp("regDate")),
				toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt"));
	}
	
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	// 게시글 조회 기능을 구현하려면 ArticleDao에 다음의 두 기능을 추가해야함 
	// 1. 특정 번호에 해당하는 게시글 데이터 읽기
	// 2. 특정 번호에 해당하는 게시글 데이터의 조회수 증가
	
	// 1. 게시글 번호로 게시글 읽기
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);
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
	
	// 조회수를 1 증가 시킬 때 사용하는 기능 구현
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("update article set read_cnt = read_cnt + 1 " + "where article_no = ?")) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	// 게시글을 수정하는 기능 구현
	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("update article set title = ?, moddate = now() " + "where article_no = ?")) {
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
	
	public int delete(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from article where article_no = ?")) {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}
}
