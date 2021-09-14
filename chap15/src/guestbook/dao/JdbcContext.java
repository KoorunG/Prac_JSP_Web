package guestbook.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Result;

import jdbc.JdbcUtil;

public class JdbcContext {	// JdbcContext는 구체클래스를 사용했는데 굳이 인터페이스화 할 필요는 없으나, 추후에 인터페이스로 변경 가능성도 있다

	public void workWithPreparedStatementStrategy(StatementStrategy strategy) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			pstmt = strategy.makePreparedStatement(); // PreparedStatement가 필요할 때 호출하여 사용
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	
	
}
