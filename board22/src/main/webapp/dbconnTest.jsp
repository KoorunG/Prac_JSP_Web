<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "jdbc.connection.ConnectionProvider" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
		try (Connection conn = ConnectionProvider.getConnection()){
			out.println("커넥션 연결 성공");
			
		} catch (SQLException e) {
			out.println("커넥션 연결 실패함 : " + e.getMessage());
			application.log("커네겻ㄴ 연결 실패", e);
		}
	%>
</body>
</html>