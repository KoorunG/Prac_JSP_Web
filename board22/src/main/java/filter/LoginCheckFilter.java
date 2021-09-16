package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 로그인 체크 필터!
// 로그인 상태라면 요청 기능을 수행하지만,
// 로그인 하지 않은 상태라면 필터를 통하여 로그인 화면으로 이동시킨다 (sendRedirect => /board22/login.do)
public class LoginCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);
		
		// 세션이 비어있거나, authUser attribute가 null이라면
		if(session == null || session.getAttribute("authUser") == null) {
			
			// 응답을 보내고
			HttpServletResponse response = (HttpServletResponse)res;
			
			// login.do 경로로 리다이렉트 시킨다
			response.sendRedirect(request.getContextPath() + "/login.do");
		} else {
			chain.doFilter(req, res);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
}
