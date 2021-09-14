<%@ tag body-content="scriptless" pageEncoding="utf-8" %>
<%@ attribute name="length" type="java.lang.Integer" %>
<%@ attribute name="trail" %>
<%@ attribute name="trim" %>

<jsp:doBody var="content" scope="page" />
<%
	String content = (String)jspContext.getAttribute("content");
	if (trim != null && trim.equals("true")) {
		content = content.trim();
	}
	content = content.replaceAll(
		"<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?>", ""); 
	
	if (length != null && length.intValue() > 0 &&
		content.length() > length.intValue()) {
		content = content.substring(0, length.intValue()); 
		if (trail != null) {
			content = content + trail;
		}
	}
	
	/*
	
	태그를 검색하기 위한 정규식
	
	1. /가 올 수도 있고 안올 수도 있다는 () 표시.
	
	2. [] 알파벳 a부터 z까지, A-Z까지 오는 모든(*) 알파벳 대상. (font 검색)
	
	3. \s 공백과 속성 알파벳 a부터 z까지, A-Z까지 오는 모든(*) 알파벳 대상.(size 검색)
	
	4. =가 있는지 체크
	
	5. >를 제외한 모든 것들 ("" 체크)
	
	*/

%>

<%= content %>