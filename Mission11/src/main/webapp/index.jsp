<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<title>메인화면</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
	<link rel="stylesheet" href="css/main.css">
</head>
<body>
	<div class="container">
	<!-- 1. 게시글 등록 -->
		<form action = "write.do">
			<input type="submit" class="btn btn-primary" value="게시글 등록"/><br><br>	
		</form>
	<!-- 2. 게시글 조회 -->
		<form action = "read.do">
			<input type="submit" class="btn btn-primary" value="최근 게시글 보기"/><br><br>	
		</form>
	
		<button type="button" class="btn btn-primary">최근 게시글 수정</button> <br><br>
		<button type="button" class="btn btn-primary">최근 게시글 삭제</button> <br><br>
	</div>
</body>
</html>