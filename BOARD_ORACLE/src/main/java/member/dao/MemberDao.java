package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.Member;

// 추후 JDBC Template이나 JPA로 대체... (복습 열심히 하자!)

public class MemberDao {
	
	// 1. 아이디로 회원 조회
	
	public Member SelectById(Connection conn, String id) throws SQLException {
		
		// PreparedStatement와 ResultSet 선언 후 초기화
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// 입력한 memberid와 일치하는 회원 검색
			pstmt = conn.prepareStatement("select * from member where memberid = ?");
			
			// setString을 이용하여 ? 자리에 아이디를 입력한다 
			pstmt.setString(1,id);
			
			// executeQuery로 쿼리 실행 후 rs 반환
			rs = pstmt.executeQuery();
			
			// Member 초기화
			Member member = null;
			
			// ResultSet을 읽은 뒤 Member객체 생성
			if(rs.next()) {
				member = new Member(
						rs.getString("memberid"),
						rs.getString("name"),
						rs.getString("password"),
						toDate(rs.getTimestamp("regDate")));
						
			}
			
			// member 반환
			return member;
			
			// finally 블록으로 확정적으로 ResultSet과 PreparedStatement를 닫아줌
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	// 2. 회원 추가
	
	public void insert(Connection conn, Member mem) throws SQLException {
		
		// PreparedStatement를 이용하여 insert 쿼리문 실행
		// try - with - resource문을 이용하여 확정적으로 리소스를 해제함 (위처럼 굳이 finally 블록을 쓰지 않아도 된다는 뜻)
		try (PreparedStatement pstmt = conn.prepareStatement("insert into member values(?,?,?,?)")) {
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getName());
			pstmt.setString(3, mem.getPassword());
			pstmt.setTimestamp(4, new Timestamp(mem.getRegDate().getTime()));
			
			// executeUpdate로 데이터 입력
			pstmt.executeUpdate();
		}
	}
	
	// sql의 Timestamp형인 date를 Date형으로 바꿔주는 메소드
	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}
	
	// update 쿼리
	
	// member 테이블의 데이터를 수정하기 위한 update() 메소드를 추가한다 (update 쿼리 날리는)
	// member.getName(), member.getPassword()로 값을 얻어와 이 값으로 DB의 데이터를 업데이트 하는 것!
	public void update(Connection conn, Member member) throws SQLException {
		try(PreparedStatement pstmt = conn.prepareStatement("update member set name = ?, password = ? where memberid =?")) {
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getId());
			pstmt.executeUpdate();
			
		}
	}
}
