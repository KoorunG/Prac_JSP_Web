<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<body>
	<table border="1">
		<tr>
			<td colspan="4"><a href="write.do">[게시글쓰기]</a></td>
		</tr>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>조회수</td>
		</tr>
		<!-- 게시글이 존재하지 않을 경우 알맞은 메세지 출력! -->
		<c:if test="${articlePage.hasNoArticles()}">
			<tr>
				<td colspan="4">게시글이 없습니다</td>
			</tr>
		</c:if>
		<!-- 게시글이 존재할 경우 게시글을 forEach를 이용하여 돌며 출력 -->
		<c:forEach var="article" items="${articlePage.content}">
			<tr>
				<td>${article.number}</td>
				<td><a
					href="read.do?no=${article.number}&pageNo=${articlePage.currentPage}">
						<c:out value="${article.title}" />
				</a></td>
				<td>${article.writer.name}</td>
				<td>${article.readCount}</td>
			</tr>
		</c:forEach>
		<c:if test="${articlePage.hasArticles()}">
			<tr>
					<!-- 페이지 이동 링크의 시작번호가 5보다 클 때 이전으로 이동할 수 있는 링크를 제공한다! ([이전]) -->
				<td colspan="4">
					<c:if test="${articlePage.startPage > 5}">
						<a href="list.do?pageNo=${articlePage.startPage - 5}">[이전]</a>
					</c:if> 
					<!-- 페이지 이동 링크를 생성한다 -->
					<c:forEach var="pNo" begin="${articlePage.startPage}"
						end="${articlePage.endPage}">
						<a href="list.do?pageNo=${pNo}">[${pNo}]</a>
					</c:forEach> 
					<!-- 페이지 이동 링크의 끝 번호가 totalPages보다 작을 경우 이동할 수 있는 링크를 제공한다! ([다음]) -->
					<c:if test="${articlePage.endPage < articlePage.totalPages}">
						<a href="list.do?pageNo=${articlePage.startPage + 5}">[다음]</a>
					</c:if>
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>