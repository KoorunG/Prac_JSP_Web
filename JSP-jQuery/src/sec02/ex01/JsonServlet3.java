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


// 여러개의 배열을 JSON으로 전달하는 웹 프로그램 제작
// bookInfo의 JSONObject 객체를 생성한 후 도서정보를 name-value 쌍으로 저장함
// bookArray의 JSONArray 객체를 생성한 후 도서 정보를 저장한 bookInfo를 차례로 저장함
// 이미 회원 배열을 저장하고 있는 totalObject의 name에는 배열 이름에는 배열 이름에 해당하는 books, value에는 bookArray를 최종적으로 저장함

@WebServlet("/json3")
public class JsonServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();

		// 배열을 최종적으로 저장할 JSONObject 객체를 생성
		JSONObject totaObject = new JSONObject();
		JSONArray membersArray = new JSONArray();
		
		// 멤버에 관한 JSONObject 객체 생성
		JSONObject memberInfo = new JSONObject();
		memberInfo.put("name", "박지성");
		memberInfo.put("age", "25");
		memberInfo.put("gender", "남자");
		memberInfo.put("nickname", "날센돌이");
		
		// membersArray에 저장
		membersArray.add(memberInfo);

		// 다른 JSONObject 객체를 생성하여 다른 회원 저장
		memberInfo = new JSONObject();
		memberInfo.put("name", "김연아");
		memberInfo.put("age", "21");
		memberInfo.put("gender", "여자");
		memberInfo.put("nickname", "칼치");
		
		// 저장
		membersArray.add(memberInfo);
		
		// 최종적으로 totalObject에 "members" 를 배열 name으로, membersArray 객체를 배열 value로 저장
		totaObject.put("members", membersArray);

		// book의 경우도 같은 로직임
		JSONArray bookArray = new JSONArray();
		JSONObject bookInfo = new JSONObject();
		bookInfo.put("title", "초보자를 위한 자바 프로그래밍");
		bookInfo.put("writer", "이병승");
		bookInfo.put("price", "30000");
		bookInfo.put("genre", "IT");
		bookInfo.put("image", "http://localhost:9001/JSP-jQuery/image/image1.jpg");
		bookArray.add(bookInfo);

		bookInfo = new JSONObject();
		bookInfo.put("title", "모두의 파이썬");
		bookInfo.put("writer", "이승찬");
		bookInfo.put("price", "12000");
		bookInfo.put("genre", "IT");
		bookInfo.put("image", "http://localhost:9001/JSP-jQuery/image/image2.jpg");
		bookArray.add(bookInfo);

		totaObject.put("books", bookArray);

		String jsonInfo = totaObject.toJSONString();
		System.out.print(jsonInfo);
		writer.print(jsonInfo);
	}

}
