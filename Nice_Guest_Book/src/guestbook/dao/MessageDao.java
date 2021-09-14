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

public class MessageDao {
	private static MessageDao messageDao = new MessageDao();
	
	public static MessageDao getInstance() {
		return messageDao;
	}
	
	private MessageDao() {}
	
	// 글 작성
	public int insert(Connection conn, Message message) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into n_guestbook_message" + "(n_guest_name, n_password, n_message) values (?,?,?)");
			pstmt.setString(1, message.getGuestName());
			pstmt.setString(2, message.getPassword());
			pstmt.setString(3, message.getMessage());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	// 단일 글 조회
	public Message select(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from n_guestbook_message where n_message_id = ?");
			pstmt.setInt(1, messageId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
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
		message.setId(rs.getInt("n_message_id"));
		message.setGuestName(rs.getString("n_guest_name"));
		message.setPassword(rs.getString("n_password"));
		message.setMessage(rs.getString("n_message"));
		return message;
	}
	
	// 글 숫자 검색
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from n_guestbook_message");
			rs.next();	// ?
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
			pstmt = conn.prepareStatement("select * from n_guestbook_message " + "order by n_message_id desc limit ?,?");
			pstmt.setInt(1, firstRow - 1);							// 1번째 파라미터 => 시작 행
			pstmt.setInt(2, endRow - firstRow + 1);				// 2번째 파라미터 => 출력할 행 수
			rs = pstmt.executeQuery();
			if(rs.next()) {
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
	
	// 글 삭제
	public int delete(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from n_guestbook_message where n_message_id = ?");
			pstmt.setInt(1, messageId);
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}
