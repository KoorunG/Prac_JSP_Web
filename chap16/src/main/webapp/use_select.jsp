<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "tf" tagdir ="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>select 태그 사용</title>
</head>
<body>
	<tf:select name="code" rgb="RGB 모드" wb="흑백 모드"/>
	<br><br>
	<tf:select name="genre" rock="락" ballard="발라드" metal="메탈"/>
</body>
</html>