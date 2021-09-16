<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 게시판 예제</title>
</head>
<body>
	
	<c:if test="${!empty authUser}">	<!-- LoginHandler에서 Attribute 설정한 authUser -->
										<!-- request.getSession().setAttribute("authUser", user); -->
										<!-- 이것이 !empty이다 => 로그인한 상태이다! -->
										<!-- JSP의 EL에는 empty 연산자가 존재하며, 의미 그대로이다 -->
		${authUser.name}님 안녕하세요.
		<a href="logout.do">[로그아웃하기]</a>
		<a href="changePwd.do">[암호변경하기]</a>
	</c:if>
	<c:if test="${empty authUser}">			<!-- 비어있다면? -->
		<a href="join.do">[회원가입하기]</a>	<!-- 이 링크를 보여줌 -->
		<a href="login.do">[로그인하기]</a>
	</c:if>
</body>
</html>