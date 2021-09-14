<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Date" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>removeHtml</title>
</head>
<body>
	<c:set var="dateEL" value="<%= new Date() %>" />
	<tf:removeHtml trim="true">
		<font size ="10">
			현재 <b>시간은</b> ${dateEL} 입니다
		</font>
	</tf:removeHtml>
	<br>
	<tf:removeHtml length ="15" trail ="..." trim = "true">
		<b>현재 시간</b>은 <b>${dateEL}</b> 입니다
	</tf:removeHtml>
	<br>
	<tf:removeHtml length="15">
		<jsp:body>
			<b>현재 시간</b>은 <b>${dateEL}</b> 입니다
		</jsp:body>
	</tf:removeHtml>
</body>
</html>