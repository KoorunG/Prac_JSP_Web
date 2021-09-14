package guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import guestbook.model.Message;

public class InsertStatement implements StatementStrategy {
	
	Connection c;
	Message message;
	
	public InsertStatement(Connection c, Message message) {
		this.c = c;
		this.message = message;
	}
	
	@Override
	public PreparedStatement makePreparedStatement() throws SQLException {
		PreparedStatement pstmt = c.prepareStatement("insert into guestbook_message" + "(guest_name, password, message) values (?,?,?)");
		pstmt.setString(1, message.getGuestName());
		pstmt.setString(2, message.getPassword());
		pstmt.setString(3, message.getMessage());
		return pstmt;
	}

}
