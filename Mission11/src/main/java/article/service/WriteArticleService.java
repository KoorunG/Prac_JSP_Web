package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

// 게시글 쓰기 기능을 제공할 WriteArticleService 클래스
public class WriteArticleService {

	// DAO 호출 및 초기화
	private ArticleDao articleDao = new ArticleDao();
	
	// 1. 게시글 작성
	// 매개변수를 이용하여 게시글을 등록하고 결과로 게시글 번호를 리턴함
	public void write(WriteRequest req) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			// 트랜잭션 시작
			conn.setAutoCommit(false); 
			
			// WriteRequest로부터 Article 객체를 생성하는 메소드 'toArticle()' 호출
			Article article = toArticle(conn, req);
			
			// ArticleDao의 insert() 메소드를 실행하고 그 결과를 변수에 할당함
			// article 테이블에 추가한 데이터의 주요 키값을 number 값으로 갖는다
			Article savedArticle = articleDao.insert(conn, article);
			
			// 만약 savedArticle == null이면 예외처리한다
			if(savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}
			
			// 위의 savedArticle 번호를 이용하여 content를 저장하는 메소드
			// ArticleContentDao의 insert() 메소드를 실행하고 그 결과를 변수에 할당함
			// 역시 savedConten == null이면 예외처리한다
			// 트랜잭션 종료 (커밋 or 롤백)
			conn.commit();
			
			// 저장된 Article의 Number를 반환한다!
			
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
	
	/**
	 * public Article(Integer number, Writer writer, String title, Date regDate, Date modifiedDate, int readCount) {
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
	}
	
	이므로...
	*/
}
