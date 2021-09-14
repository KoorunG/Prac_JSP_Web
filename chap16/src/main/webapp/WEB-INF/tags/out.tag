<%-- tagdependent : 전달되어지는 jspbody의 표현언어 EL을 그냥 문자열로 인식하게 함 --%>
<%@ tag body-content="tagdependent" pageEncoding="utf-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:doBody var="bodyText" />

<%-- escapeXml : 브라우저 상에서 태그의 <>를 문자열로 인식시켜주는 속성 --%>
<c:out value="${bodyText}" escapeXml="true" />