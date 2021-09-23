<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
</head>
<body>
	게시글을 등록했습니다!
	<!-- WriteArticleHandler가 뷰로 사용하는 newArticleForm.jsp를 제작한다 (게시글 쓰기 폼을 제공하는 역할을 한다) -->
	<br>		
	${ctxPath = pageContext.request.contextPath; ''}
	<!-- 홀따옴표 두개임.. EL문법 -->
	<!-- 게시글 목록 보기 이동 링크 제공 -->
	<a href = "${ctxPath}/article/list.do">[게시글목록보기]</a>
	<!-- 게시글 내용 보기 이동 링크 제공 (이때는 게시글 내용 보기 링크를 생성할 때 조회할 게시글 번호가 필요하므로 ${newArticleNo}를 쿼리스트링으로 받음 -->
	<a href = "${ctxPath}/article/read.do?no=${newArticleNo}">[게시글내용보기]</a>
</body>
</html>