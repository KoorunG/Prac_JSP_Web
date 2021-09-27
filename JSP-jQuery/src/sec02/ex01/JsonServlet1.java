package sec02.ex01;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 서버의 서블릿과 JSON 자료를 주고 받는 기능을 구현하기 위해
// 서버의 서블릿에서 JSON을 사용하려면 JSON 라이브러리를 활용해야 함 => JSON Simple
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// 다음은 jQuery와 Ajax를 이용하여 서버와 JSON 데이터를 주고 받기 위한 JsonServlet1 클래스 소스
// Ajax로 전달된 JSON 문자열을 getParameter() 메소드를 이용하여 가져옴
// 그런 다음 JSONParser 클래스의 parse() 메소드를 이용하여 JSONObject를 가져오고 JSON 데이터의 name 속성으로 value를 출력함

@WebServlet("/json")
public class JsonServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// 문자열로 전송된 JSON 데이터를 getParameter()를 이용해 가져옴
		String jsonInfo = request.getParameter("jsonInfo");
		try {
			
			// 파싱하기 위한 JSONParser() 객체
			JSONParser jsonParser = new JSONParser();
			
			// jsonParser를 이용하여 JSON 데이터 파싱 후 jsonObject에 저장
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonInfo);
			
			// jsonObject를 이용하여 get() 메소드를 이용해 값을 꺼내 차례대로 출력함
			System.out.println("* 회원 정보*");
			System.out.println(jsonObject.get("name"));
			System.out.println(jsonObject.get("age"));
			System.out.println(jsonObject.get("gender"));
			System.out.println(jsonObject.get("nickname"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
