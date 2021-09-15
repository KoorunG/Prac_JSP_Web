package filter;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

// NullParameterRequestWrapper를 사용해서 요청파라미터를 처리하는 필터 클래스

public class NullParameterFilter implements Filter{

	private String[] parameterNames = null;
	
	// 초기화 메소드
	@Override
	public void init(FilterConfig config) throws ServletException {
		
		// StringTokenizer을 이용해 , 를 기준으로 파라미터의 이름을 잘라서 토큰의 수 만큼의 크기를 가진 배열 parameterNames에 저장함
		// getParameterNames()의 리턴 타입이 Enumeration<String> 이므로 ,로 값이 나눠진거임...?
		
		String names = config.getInitParameter("parameterNames");
		StringTokenizer st = new StringTokenizer(names,", ");
		
		parameterNames = new String[st.countTokens()];
		
		// StringTokenizer을 이용하여 자른 토큰들을 parameterNames에 저장하는 과정
		for(int i = 0 ; st.hasMoreTokens(); i++) {
			parameterNames[i] = st.nextToken();
		}
	}

	// 필터 동작 메소드
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		NullParameterRequestWrapper requestWrapper = new NullParameterRequestWrapper((HttpServletRequest)request);
		
		// checkNull 동작
		requestWrapper.checkNull(parameterNames);
		
		// 필터체인으로 다음 필터로 넘기는 메소드
		chain.doFilter(requestWrapper, response);
	}

	// 종료 메소드
	@Override
	public void destroy() {
	}
	
}
