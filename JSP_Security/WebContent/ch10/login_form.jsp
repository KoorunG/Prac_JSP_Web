<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>Security</title>
</head>
<body>    <!-- 로그인 인증 처리를 위한 form 태그를 작성합니다 -->
	<!-- 폼 기반 인증을 처리하도록 action 속성 값을 j_security_check로 작성합니다 -->
	<form name="loginForm" action="j_security_check" method="post">
		<!-- 폼 기반 인증을 처리하도록 사용자명과 비밀번호의 name 속성 값을 각각
                                    j_username, j_password로 작성합니다 -->
		<p>사용자명: <input type="text" name="j_username">
		<p>비밀번호 : <input type="password" name="j_password">
		<p><input type="submit" value="전송">
	</form>
</body>
</html>