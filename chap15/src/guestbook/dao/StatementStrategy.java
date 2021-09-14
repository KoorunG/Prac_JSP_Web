package guestbook.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {	
	PreparedStatement makePreparedStatement() throws SQLException;
}
