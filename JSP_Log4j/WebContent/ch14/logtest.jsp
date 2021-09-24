<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*, org.slf4j.*"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> log4j 테스트</title>
</head>
<body>
<div align="center">
<H2>logtest.jsp</H2>
<HR>
로그 테스트로 화면 입니다. 콘솔 메시지와 C드라이브에 tmp 폴더 dailyout.log 파일에 로그가 생성 됩니다.
여기서, C드라이브에 tmp 폴더 dailyout.log 파일 경로는 src 폴더에 있는 log4j.xml 파일에 있는 경로입니다.
<%
	// 시스템 기본 로그를 통해 처리함.
	application.log("logtest.jsp:테스트 로그 메시지...",new IOException());

	Logger log = LoggerFactory.getLogger(this.getClass());
	log.info("info-jsp 파일에서 처리한 로그"); 
	log.warn("warn-jsp 파일에서 처리한 로그"); 
%>

</div>
</body>
</html>