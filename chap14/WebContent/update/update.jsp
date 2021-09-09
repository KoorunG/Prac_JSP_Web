<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<% 
request.setCharacterEncoding("utf-8");
String memberID = request.getParameter("memberID");
String name = request.getParameter("name");

int updateCount = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
		<%
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysql 8.x 이하 : com.mysql.jdbc.Driver
														// mysql 8.x 이상 : com.mysql.cj.jdbc.Driver
			System.out.println("1. JDBC 드라이버 로딩");
			Connection conn = null;
			Statement stmt = null;

			try {
				String jdbcDriver = "jdbc:mysql://localhost:3306/chap14" 
									+"?characterEncoding=UTF-8"
									+ "&serverTimezone=UTC" 
									+ "&useSSL=false" 
									+ "&allowpublickeyretrieval=true";

				String dbUser = "jspexam";
				String dbPass = "jsppw";
				String query = "update MEMBER set NAME = '" + name + "'" + "where memberid = " + "'" + memberID + "'";

				// 2. Connection 생성
				conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
				
				System.out.println("2. Connection 생성");

				// 3. Statement 생성
				stmt = conn.createStatement();
				updateCount = stmt.executeUpdate(query);
				
				System.out.println("3. Statement 생성");
			}  catch (SQLException ex) {
				out.println(ex.getMessage());
				ex.printStackTrace();
			} finally {
				
				// 7. Statement 종료
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException ex) {
					}
				}

				System.out.println("7. Statement 종료 ");
				
				// 8. Connection 종료
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException ex) {
					}
				}
				
				System.out.println("8. Connection 종료 ");
			}
		%>
		<% if(updateCount == 0) { %>
		변경된 데이터 없음
		<% } else { %>
		<h3>업데이트 완료</h3><br><br>
		<%= memberID %> 의 이름이 <%= name %> 으로 변경되었습니다! <br>
		<% } %>
		
</body>
</html>