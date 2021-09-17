package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

// 사용자가 입력한 아이디와 암호가 일치하면 로그인한 사용자 정보를 담은 User 객체를 리턴함
public class LoginService {
	
	// MemberDao 객체를 생성 => DB 접근
	private MemberDao memberDao = new MemberDao();
	
	// 로그인 로직
	// 아이디와 패스워드를 받아서 조건을 만족하면
	// User 객체를 생성하여 반환한다
	public User login(String id, String password) {
		
		// try-with-resource문
		try(Connection conn = ConnectionProvider.getConnection()){
			
			// DB에서 select하여 member객체를 얻은 뒤
			Member member = memberDao.SelectById(conn, id);
			
			// 1. member == null이라면 (즉 아이디로 검색이 되지 않았다면) 예외 발생
			if(member == null) {
				throw new LoginFailException();
			}
			
			// 2. 패스워드가 일치하지 않는다면 예외 발생
			if(!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			
			// 조건을 모두 만족했다면 User 객체 생성 후 반환
			return new User(member.getId(), member.getName());
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/** 
	 * 
	 * Join의 경우 JoinService 클래스의 join() 메소드는 JoinRequest 클래스를 이용하여 필요한 데이터를 전달받았는데
	 * Login의 경우 LoginService 클래스의 login() 메소드는 필요한 데이터가 아이디와 암호 뿐이어서 데이터를 담는 별도의 클래스를 만들지 않았다
	 * => (바로 id와 password를 파라미터로 전달받음)
	 *  
	 *  */
}
