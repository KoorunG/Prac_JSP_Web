package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class ChangePasswordService {

	private MemberDao memberDao = new MemberDao();

	// 암호 변경 기능 메소드 changePassword
	// userId는 암호를 변경할 회원 아이디
	// curPwd : 현재 비밀변호, newPwd : 바꿀 비밀번호
	public void changePassword(String userId, String curPwd, String newPwd) {
		Connection conn = null;

		// 요청 방식이 GET(또는 get)이면 processForm()을 실행하고,
		// 요청 방식이 POST(또는 post)이면 processSubmit()을 실행한다.
		// 참고로, equls() 메서드는 문자열 비교 시 대소문자를 구분해서,
		// 문자열이 같은 경우 true 리턴하고, 다른 경우 false 리턴함.
		// 하지만, equalsIgnoreCase() 메서드는 문자열 비교 시 대소문자를
		// 구분하지 않음. 즉, 대소문자 구분 없이 문자열만 같다면 true 리턴하고,
		// 다른 경우 false 리턴함.
		// GET 또는 POST가 아니면 405 응답 코드를 전송한다.
		// 참고로, static int SC_METHOD_NOT_ALLOWED
		// 405 응답 코드 전송 (허용되지 않는 메소드 응답)
		// 지정된 메서드가 식별 된 리소스에 대해 허용되지 않음을 나타내는
		// 상태 코드 (405)를 의미함.

		try {
			conn = ConnectionProvider.getConnection();

			// 트랜잭션 시작
			conn.setAutoCommit(false);

			Member member = memberDao.SelectById(conn, userId);
			// 각 예외에 따라 아까 커스텀으로 만든 예외로 처리를 해준다!
			if (member == null) {
				throw new MemberNotFoundException();
			}
			if (!member.matchPassword(curPwd)) {
				throw new InvalidPasswordException();
			}

			// 예외가 발생하지 않았다면 파라미터로 받은 newPwd로 비밀번호를 바꿔준다
			member.changePassword(newPwd);

			// 그리고 memberDao.update() 를 통해 DB를 업데이트 한다
			memberDao.update(conn, member);

			// 트랜잭션 종료
			conn.commit();

			// 만일 기타 예외가 발생했을 경우 => 롤백처리로 트랜잭션 종료
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
