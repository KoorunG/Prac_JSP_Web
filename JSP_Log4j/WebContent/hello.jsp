<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>Hello JSP!</h1>
	Hello Java Server Page~
	안녕하세요 반가워요 잘있어요 다시만나요~
	<br><br>
	<c:set var="hello" value="안녕~"/>
	${hello}
</body>
</html>