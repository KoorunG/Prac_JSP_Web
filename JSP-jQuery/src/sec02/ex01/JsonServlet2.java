package sec02.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


// JSON 배열에 회원 정보를 저장하여 JSP 페이지로 전송하고
// JSON 배열에 정보를 저장하는 과정은 다음과 같다
// 1. memberInfo로 JSONObject 객체를 생성한 후 회원 정보를 name-value 쌍으로 저장함
// 2. membersArray의 JSONArray 객체를 생성한 후 회원 정보를 저장한 JSON 객체를 차례대로 저장함
// 3. membersArray 배열에 회원정보를 저장한 후 totalObject로 JSONObject 객체를 생성하여
//    name에는 자바스크립트에서 접근할 때 사용하는 이름인 "members"를 value에는 membersArray를 최종적으로 저장함

@WebServlet("/json2")
public class JsonServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();

		// 배열을 저장할 totalObject 객체 생성
		JSONObject totalObject = new JSONObject();
		
		// memberInfo JSON 객체를 저장할 membersArray 배열 객체 생성 
		JSONArray membersArray = new JSONArray();
		
		// 회원 한 명의 정보가 들어갈 memberInfo 객체를 생성함
		JSONObject memberInfo = new JSONObject();

		
		// 회원정보를 입력 (name-value쌍)
		memberInfo.put("name", "박지성");
		memberInfo.put("age", "25");
		memberInfo.put("gender", "남자");
		memberInfo.put("nickname", "날센돌이");
		
        // membersArray 배열에 입력
		membersArray.add(memberInfo);

		// 다른 회원 정보를 '객체를 새로 생성하여' name-value쌍으로 저장한 후 membersArray에 다시 저장함
		memberInfo = new JSONObject();
		memberInfo.put("name", "김연아");
		memberInfo.put("age", "21");
		memberInfo.put("gender", "여자");
		memberInfo.put("nickname", "칼치");
		membersArray.add(memberInfo);

		// 최종적으로 membersArray 배열 객체들을(여기서는 둘) totalObject에 저장함
		totalObject.put("members", membersArray);

		// toJSONString() 메소드를 이용하여 JSON 배열을 String으로 변환함
		String jsonInfo = totalObject.toJSONString();
		
		// 1. 콘솔에 출력
		System.out.print(jsonInfo);
		
		// 2. 브라우저로 전송
		writer.print(jsonInfo);
	}

}
