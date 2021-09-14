package guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import guestbook.model.Message;

public class deleteStatement implements StatementStrategy {
	
	Connection c;
	Message message;
	
	public deleteStatement(Connection c, Message message) {
		this.c = c;
		this.message = message;
	}
	

	@Override
	public PreparedStatement makePreparedStatement() throws SQLException {
		PreparedStatement pstmt = c.prepareStatement("delete from guestbook_message where message_id = ?");
		pstmt.setInt(1, message.getId());
		return pstmt;
	}

	
}
