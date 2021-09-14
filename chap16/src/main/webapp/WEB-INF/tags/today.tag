<%@ tag body-content="empty" pageEncoding="UTF-8"%> <%-- 디렉티브가 tag로 되어있음 --%>
<%@ tag import="java.util.Calendar" %>
<%
	Calendar cal = Calendar.getInstance();
%>

<%= cal.get(Calendar.YEAR) %>년
<%= cal.get(Calendar.MONTH)+1 %>월
<%= cal.get(Calendar.DATE) %>일