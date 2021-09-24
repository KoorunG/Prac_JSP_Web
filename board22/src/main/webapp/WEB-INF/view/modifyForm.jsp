<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>

<!-- 게시글 수정 폼을 보여주는 modifyForm.jsp 파일 -->
<!-- ModifyArticleHandler에서 전달받은 ModifyRequest 객체를 이용하여 폼에 데이터를 채움 -->
<!-- 실제 로그인을 하고 게시글 읽기 화면에서 [게시글 수정] 링크를 클릭하면 수정 폼이 출력되도록 설정함 -->

<body>
	<form action="modify.do" method="post">
		<input type="hidden" name="no" value="${modReq.articleNumber}">
		<p>
			번호 : <br/>${modReq.articleNumber}
		</p>
		<p>
			제목 : <br/><input type="text" name="title" value="${modReq.title}">
			<c:if test="${errors.title}">제목을 입력하세요</c:if>
		</p>
		<p>
			내용 : <br/>
			<textarea name="content" rows="5" cols="30">${modReq.content}</textarea>
		</p>
		<input type="submit" value="글 수정">
	</form>
</body>
</html>