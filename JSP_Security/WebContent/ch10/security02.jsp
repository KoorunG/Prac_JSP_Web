<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>Security</title>
</head>
<body>   <!-- 인증된 사용자를 가져오도록 request 내장 객체의 getRemoteUser() 메소드를 작성합니다 -->
	<p>사용자명 : <%=request.getRemoteUser()%>
	<!-- 인증 처리 방식을 가져오도록 request 내장 객체의 getAuthType() 메소드를 작성합니다 -->

	<p>인증방법 : <%=request.getAuthType()%>

	<!-- 로그인 시 인증한 사용자의 역할 이름이 tomcat인지, role1인지 확인하도록
                      request 내장 객체의 isUserInRole() 메소드를 작성합니다 -->
	<p>인증한 사용자명이 역할명 "tomcat"에 속하는 사용자인가요?
		<%=request.isUserInRole("tomcat")%>

	<p>인증한 사용자명이 역할명 "role1"에 속하는 사용자인가요?
		<%=request.isUserInRole("role1")%>  
		
	<p>인증한 사용자명이 역할명 "admin"에 속하는 사용자인가요?
		<%=request.isUserInRole("admin")%>  
</body>
</html>