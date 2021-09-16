package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class JoinService {
	
	private MemberDao memberDao = new MemberDao();
	
	public void join(JoinRequest joinReq) {
		Connection conn = null;
		
		try {
			// DB 커넥션을 구함
			conn = ConnectionProvider.getConnection();
			
			// 트랜잭션 시작
			conn.setAutoCommit(false);
			
			// member를 id로 검색하여 존재하면
			// DuplicatedIdException 예외를 보내고 롤백처리함
			// MemberDao의 selectById 메소드를 이용하여 joinReq.getId()에 해당하는 회원 데이터를 구함
			Member member = memberDao.SelectById(conn, joinReq.getId());
			
			// 그 ID에 해당하는 데이터가 이미 존재하면 롤백처리하고 중복 예외처리
			if(member != null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			
			// 멤버를 insert하고 
			memberDao.insert(conn, new Member(joinReq.getId(),joinReq.getName(), joinReq.getPassword(), new Date()));
			// 커밋 처리함 (트랜잭션 끝)
			conn.commit();
			
			// 처리도중 SQL 예외가 발생하면 롤백처리하고 runtime 예외를 던져줌
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
