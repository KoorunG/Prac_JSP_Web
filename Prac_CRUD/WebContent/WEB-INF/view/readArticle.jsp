<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>메인화면</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link rel="stylesheet" href="../css/main.css">
<%
pageContext.setAttribute("newLineChar", "\n");
%>
<c:set var="data" value="${articleData.content}" />
</head>
<body>
	<div class="container">
		<div class="box-wrapper">
			<p>제목 :
			<div id="title_box">${articleData.title}</div>
			</p>
			<!-- 커스텀태그가 이상하게 먹어서 이렇게 했습니다 -->
			<p>글 내용 :
			<div id="content_box">${fn:replace(data, newLineChar, "<br> ")}</div>

			</p>
			<form action="/Prac_CRUD/index.jsp">
				<input type="submit" value="처음으로">
			</form>
		</div>
	</div>
</body>
</html>