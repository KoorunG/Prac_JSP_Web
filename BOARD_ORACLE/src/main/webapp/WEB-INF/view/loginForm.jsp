<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<!-- 로그인 폼을 보여줄 loginForm.jsp 문서 작성 -->
<!-- 프로퍼티 파일에서 LoginHandler를 /login.do으로 매핑했으므로 form action을 login.do로 설정한다 -->
	<form action="login.do" method = "POST">
	
	<!-- 1. errors의 attribute로 idOrPwNotMatch가 존재한다면 -->
		<c:if test="${errors.idOrPwNotMatch}">
		<!-- 아이디 또는 암호가 일치하지 않는다는 메세지 출력 -->
		아이디와 암호가 일치하지 않습니다
		</c:if>
		<p> <!-- 입력값을 파라미터의 아이디 ${param.id}로 보냄 -->
			아이디 : <br/><input type="text" name="id" value="${param.id}">
			<c:if test="${errors.id}">ID를 입력하세요</c:if>
		</p>
		<p><!-- 이하동문 -->
			암호 : <br/><input type="password" name="password" value="${param.password}">
			<c:if test="${errors.password}">암호를 입력하세요</c:if>
		</p>
		<input type="submit" value="로그인">
	</form>
</body>
</html>