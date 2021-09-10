<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
	MEMBER 테이블의 내용
	<table width="100%" border="1">
		<tr>
			<td>이름</td>
			<td>아이디</td>
			<td>이메일</td>
		</tr>
		<%
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String jdbcDriver = "jdbc:apache:commons:dbcp:chap14";	// => 커넥션풀을 연결해놓은 드라이버!
			String query = "select * from MEMBER order by MEMBERID";
			conn = DriverManager.getConnection(jdbcDriver);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
		%>
		<tr>
			<!-- table row에 동적으로 데이터 담음 -->
			<td><%=rs.getString("NAME")%></td>
			<td><%=rs.getString("MEMBERID")%></td>
			<td><%=rs.getString("EMAIL")%></td>
		</tr>
		<%
		}
		} catch (SQLException ex) {
		out.println(ex.getMessage());
		ex.printStackTrace();
		} finally {
		if (rs != null) {
		try {
			rs.close();
		} catch (SQLException ex) {
		}
		}

		if (stmt != null) {
		try {
			stmt.close();
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
	</table>
</body>
</html>