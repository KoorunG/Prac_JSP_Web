<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("utf-8");
String memberID = request.getParameter("memberID");
String password = request.getParameter("password");
String name = request.getParameter("name");
String email = request.getParameter("email");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삽입</title>
</head>
<body>
	<%
    // Class.forName("com.mysql.cj.jdbc.Driver"); // mysql 8.x 이하 : com.mysql.jdbc.Driver
										// mysql 8.x 이상 : com.mysql.cj.jdbc.Driver
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
		String jdbcDriver = "jdbc:mysql://localhost:3306/chap14" 
							+ "?characterEncoding=UTF-8" 
							+ "&serverTimezone=UTC"
							+ "&useSSL=false" 
							+ "&allowpublickeyretrieval=true";

		String dbUser = "jspexam";
		String dbPass = "jsppw";
		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

		String query = "insert into member values(?, ?, ?, ?)";
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, memberID);
		pstmt.setString(2, password);
		pstmt.setString(3, name);
		pstmt.setString(4, email);

		pstmt.executeUpdate();
		pstmt.executeQuery("commit");

	} catch (SQLException ex) {
		out.println(ex.getMessage());
		ex.printStackTrace();
	} finally {

		if (pstmt != null) {
			try {
		pstmt.close();
			} catch (SQLException ex) {
			}
		}

		if (conn != null) {
			try {
		conn.close();
			} catch (SQLException ex) {
			}
		}

	}
	%>
	MEMBER 테이블에 새로운 레코드를 삽입했습니다
</body>
</html>