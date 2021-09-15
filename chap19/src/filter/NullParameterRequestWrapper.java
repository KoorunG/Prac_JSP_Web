package filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// 지정한 파라미터가 존재하지 않을 경우 파라미터의 값을 공백 문자열로 제공하는 요청 래퍼 클래스...
// 요청 래퍼 클래스로 동작하기 위해 HttpServletRequestWrapper 클래스를 상속받는다

public class NullParameterRequestWrapper extends HttpServletRequestWrapper {

	// parameterMap 지정 (쿼리스트링으로 파라미터가 여러개가 올 수 있기 때문에 Map 타입으로 만듦)
	private Map<String, String[]> parameterMap = null;
	
	// 생성자로 요청의 파라미터맵을 받아서 넣고 parameterMap 변수로 지정함
	public NullParameterRequestWrapper(HttpServletRequest request) {
		super(request);
		parameterMap = new HashMap<String,String[]>(request.getParameterMap());
	}
	
	// Null인지 검사하는 부분
	// 파라미터 맵의 이름으로 검색하여 지정한 파라미터가 존재하지 않을 경우 파라미터의 값으로 공백을 집어넣음
	public void checkNull(String[] parameterNames) {
		for(int i = 0 ; i < parameterNames.length ; i++) {
			if(!parameterMap.containsKey(parameterNames[i])) {
				String[] values = new String[]{""};
				parameterMap.put(parameterNames[i], values);
			}
		}
	}

	// 파라미터가 하나일 경우 간단하게 파라미터값을 가져오는 메소드
	@Override
	public String getParameter(String name) {
		String[] values = getParameterValues(name);
		if(values != null && values.length > 0) {
			return values[0];
		}
		return null;
	}

	// 파라미터가 여러개일 경우 파라미터맵에 넣는 메소드
	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	// 파라미터 이름을 Set형식의 집합으로 넣는 메소드
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameterMap.keySet());
	}

	// 파라미터의 값을 배열로 넣는 메소드
	@Override
	public String[] getParameterValues(String name) {
		return (String[])parameterMap.get(name);
	}
	
	
}
