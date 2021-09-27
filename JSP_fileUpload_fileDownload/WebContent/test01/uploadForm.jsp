<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>    
<html>
<head>
<meta charset="UTF-8">
 <head>
   <title>파일 업로드창</title>
 </head> <body>
 
 <!-- 파일업로드 창에서 파일을 업로드 할 때 enctype="multipart/form-data" 속성을 지정해줘야 한다  -->
 <!-- upload.do => 서블릿에 요청해 파일을 업로드함 (FileUpload.java 클래스에 @WebSocket("/upload.do") 로 매핑되어있음 -->
   <form action="${contextPath}/upload.do"  method="post" enctype="multipart/form-data" >
      파라미터1: <input type="text" name="param1" > <br> <br>  
      파일1: <input type="file" name="file1" ><br> <br>  
      파라미터2: <input type="text" name="param2" > <br> <br> 
      파일2: <input type="file" name="file2" > <br> <br>  
 <input type="submit" value="업로드" >
</form>
 </body>
</html>
