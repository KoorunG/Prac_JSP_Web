<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 읽기</title>
</head>
<body>
	<table border="1" width="100%">
		<tr>
			<td>제목</td>
			<td><c:out value='${articleData.title}' /></td>
		</tr>
		<tr>
			<td>내용</td>
			<% pageContext.setAttribute("newLineChar", "\n"); %>
			<c:set var="data" value="${articleData.content}"/>
			<td>${fn:replace(data, newLineChar, "<br> ")}</td>
		</tr>
	</table>
</body>
</html>