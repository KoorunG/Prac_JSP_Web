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
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysql 8.x 이하 : com.mysql.jdbc.Driver
														// mysql 8.x 이상 : com.mysql.cj.jdbc.Driver
			System.out.println("1. JDBC 드라이버 로딩");
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				String jdbcDriver = "jdbc:mysql://localhost:3306/chap14" 
									+"?characterEncoding=UTF-8"
									+ "&serverTimezone=UTC" 
									+ "&useSSL=false" 
									+ "&allowpublickeyretrieval=true";

				String dbUser = "jspexam";
				String dbPass = "jsppw";
				String query = "select * from MEMBER order by MEMBERID";

				// 2. Connection 생성
				conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
				
				System.out.println("2. Connection 생성");

				// 3. Statement 생성
				stmt = conn.createStatement();

				System.out.println("3. Statement 생성");
				
				// 4. ResultSet 생성 - Query 실행
				rs = stmt.executeQuery(query);

				System.out.println("4. ResultSet 생성 - Query 실행");
				
				// 5. Query 결과 출력
				while (rs.next()) {
		%>
		<tr>	<!-- table row에 동적으로 데이터 담음 -->
			<td><%=rs.getString("NAME")%></td>
			<td><%=rs.getString("MEMBERID")%></td>
			<td><%=rs.getString("EMAIL")%></td>
		</tr>
		<%
			} System.out.println("5. Query 결과 출력");
			} catch (SQLException ex) {
				out.println(ex.getMessage());
				ex.printStackTrace();
			} finally {
				// 6. ResultSet 종료 
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException ex) {
					}
				}

				System.out.println("6. ResultSet 종료 ");
				
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
	</table>
</body>
</html>