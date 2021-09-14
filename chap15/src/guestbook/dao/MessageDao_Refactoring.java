package guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import guestbook.model.Message;
import jdbc.JdbcUtil;

public class MessageDao_Refactoring {	// 1. 클라이언트
	private static MessageDao_Refactoring messageDao = new MessageDao_Refactoring();

	public static MessageDao_Refactoring getInstance() {
		return messageDao;
	}

	private MessageDao_Refactoring() {
	}

	// 스프링을 쓴다면...
	
	// (DataSource가 있고 jdbcContext가 이를 주입받아야 하는 상황이라면 => 이렇게 jdbcContext가 스프링빈으로 등록되지 않은 상황에서는 DI를 받을 수 없다.
	// 따라서 jdbcContext의 제어권을 가지고 있는 DAO 파일에서 DI까지 담당할 수 있다!
	// DAO는 DataSource가 직접적으로 필요하지는 않지만, JdbcContext에 대한 DI 작업에 사용할 용도로 DI를 대신 받을 수 있다
	// 즉 bean으로 등록하는 것은 dataSource와 DAO파일 둘으로 충분해진다!
	
	// 이렇게 한 오브젝트의 수정자 메소드에서 다른 오브젝트를 초기화하고 코드를 이용해 DI를 하는 것은 "스프링에서도 종종 사용되는 기법" 이다
	
	



	private JdbcContext jdbcContext; // DI받은 jdbcContext; => 스프링빈으로 주입받을 것이 아니라면 DAO마다 하나의 JDBC Context를 가지게끔..
	
	public void setJdbcContext(JdbcContext jdbcContext) {	// JdbcContext를 DI 받음 (근데 굳이 메소드로 받을 필요가 있을까?)
		this.jdbcContext = jdbcContext;
	}

	// 클라이언트 책임을 담당하는 insert 메소드

	public void insert(Connection conn, Message message) throws SQLException {
		
		this.jdbcContext.workWithPreparedStatementStrategy(new StatementStrategy() {	// 2. new StatementStrategy()를 통해 콜백 생성
																						// 3. JdbcContext => 템플릿 호출(정확히는 템플릿의 메소드 호출)
			
			// 주의점 : 내부클래스에서 외부의 변수를 사용할 때는 외부 변수를 반드시 final로 선언해야 함
			// (final로 선언되어있지 않더라도 잠정적으로 final취급이긴 하다..?)
			// (effective final)
			@Override
			public PreparedStatement makePreparedStatement() throws SQLException {		// 4. 콜백에서 템플릿으로부터 전달받은 Connection, Message(?) 등의 정보를 이용하여
																						// PreparedStatement 생성
				PreparedStatement pstmt = conn.prepareStatement("insert into guestbook_message" + "(guest_name, password, message) values (?,?,?)");
				pstmt.setString(1, message.getGuestName());
				pstmt.setString(2, message.getPassword());
				pstmt.setString(3, message.getMessage());
				return pstmt;															// 5. 템플릿에서 콜백으로부터 전달받은 pstmt를 이용하여 결과 만듦
			}
		}); // 컨텍스트 호출 - 전략 오브젝트 전달
	}

	// 리팩토링한 delete 메소드
	public void delete(Connection conn, Message message) throws SQLException {
		
		this.jdbcContext.workWithPreparedStatementStrategy(new StatementStrategy() {
			
			// 익명 내부클래스로 구현 (어차피 메소드 내에서 구체 전략을 한번만 사용하므로 굳이 내부클래스로 선언하고 변수에 담아둘 필요가 없다)
			@Override
			public PreparedStatement makePreparedStatement() throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement("delete from guestbook_message where message_id = ?");
				pstmt.setInt(1, message.getId());
				return pstmt;
			}
		});
	}

	// 단일 글 조회
	public Message select(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from guestbook_message where message_id = ?");
			pstmt.setInt(1, messageId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return makeMessageFromResultSet(rs);
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// 읽어온 ResultSet에서 message를 만들어주는 메소드
	private Message makeMessageFromResultSet(ResultSet rs) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt("message_id"));
		message.setGuestName(rs.getString("guest_name"));
		message.setPassword(rs.getString("password"));
		message.setMessage(rs.getString("message"));
		return message;
	}

	// 글 숫자 검색
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from guestbook_message");
			rs.next(); // ?
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	// 글 리스트 검색
	public List<Message> selectList(Connection conn, int firstRow, int endRow) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from guestbook_message " + "order by message_id asc limit ?,?");
			pstmt.setInt(1, firstRow - 1); // 1번째 파라미터 => 시작 행
			pstmt.setInt(2, endRow - firstRow + 1); // 2번째 파라미터 => 출력할 행 수
			rs = pstmt.executeQuery();
			if (rs.next()) {
				List<Message> messageList = new ArrayList<>();
				do {
					messageList.add(makeMessageFromResultSet(rs));
				} while (rs.next());
				return messageList;
			} else {
				return Collections.emptyList();
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}
