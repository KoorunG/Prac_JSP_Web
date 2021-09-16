package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

// 로그아웃 핸들러
public class LogoutHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 요청으로부터 세션을 얻어온 뒤
		HttpSession session = request.getSession(false);
		
		// 세션이 null이 아니라면
		if(session != null) {
			
			// 세션을 해제한다!
			session.invalidate();
		}
		
		// 그리고 index.jsp로 리다이렉트
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return null;
	}
	
	// 매우 간단한 로직... session.invalidate() 메소드를 이용하여 세션을 해제!
	
}
