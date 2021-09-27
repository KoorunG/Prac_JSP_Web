<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>       
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("utf-8");
%>
<html>
<head>
<meta charset=”UTF-8">
<c:set var="file1" value="${param.param1}"  />    
<c:set var="file2" value="${param.param2}"  />
 
<title>이미지 파일 출력하기</title>
</head>
<body>

<!-- img 태그의 src 속성에 경로를 지정하여 미리보기를 지원함 -->
<!-- <a> 태그를 걸어 다운로드를 호출하도록 함 ( @WebServlet을 /download.do로 지정했으며 뒤에 오는 파라미터를 다운로드 받는 로직을 가진 자바파일 FileDownload.java -->
파라미터 1 :<c:out value="${file1}"  /><br>
<c:if test="${not empty file1 }">
<img src="${contextPath}/download.do?fileName=${file1}"  width=300 height=300 /><br>
</c:if>
파일 내려받기(1) :<br>
<a href="${contextPath}/download.do?fileName=${file1}" >파일 내려받기</a><br>
<br><br>

파라미터 2 :<c:out value="${file2}"  /><br>
<c:if test="${not empty file2 }">
<img src="${contextPath}/download.do?fileName=${file2}"  width=300 height=300 /><br>
</c:if>
파일 내려받기(2) :<br>
<a href="${contextPath}/download.do?fileName=${file2}" >파일 내려받기</a><br>
</body>
</html>
