<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String memberID = request.getParameter("memberID");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<body>
	<%
	// 1. JDBC 드라이버 로딩
	Class.forName("com.mysql.cj.jdbc.Driver"); // mysql 8.x 이하 : com.mysql.jdbc.Driver
										// mysql 8.x 이상 : com.mysql.cj.jdbc.Driver
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	try {
		String jdbcDriver = "jdbc:mysql://localhost:3306/chap14" + "?characterEncoding=UTF-8" + "&serverTimezone=UTC"
		+ "&useSSL=false" + "&allowpublickeyretrieval=true";

		String dbUser = "jspexam";
		String dbPass = "jsppw";
		

		// 2. Connection 생성
		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

		// 3. Statement 생성
		// 4. ResultSet 생성
		stmt = conn.createStatement();
		String query = "select * from member where memberid = '" + memberID + "'";
		rs = stmt.executeQuery(query);
		if (rs.next()) {
	%>
	<!--  동적으로 테이블 생성 -->
	<table border="1">
		<tr>
			<td>아이디</td>
			<td><%=memberID%></td>
		</tr>
		<tr>
			<td>암호</td>
			<td><%=rs.getString("password")%></td>
		</tr>
		<tr>
			<td>이름</td>
			<td><%=rs.getString("name")%></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><%=rs.getString("email")%></td>
		</tr>
	</table>
	<%
	} else {
	%>
	<!--  파라미터가 존재하지 않을경우 메세지 출력 -->
	<%=memberID%>에 해당하는 정보가 존재하지 않습니다!
	<%
	}
	} catch (SQLException ex) {
	%>
	에러 발생 :
	<%=ex.getMessage()%>
	<%
	} finally {
		
		// 5. ResultSet 종료
	if (rs != null) {
		try {
			rs.close();
		} catch (SQLException ex) {
		}
	}
		// 6. Statement 종료
	if (stmt != null) {
		try {
			stmt.close();
		} catch (SQLException ex) {
		}
	}
		// 7. Connection 종료
	if (conn != null) {
		try {
			conn.close();
		} catch (SQLException ex) {
		}
	}

	}
	%>

</body>
</html>