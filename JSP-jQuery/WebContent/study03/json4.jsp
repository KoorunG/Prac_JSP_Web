<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"  %>

<%@ taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSON 테스트</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- JSON 배열의 요소에 JSON 객체를 저장한 후 다시 배열에 접근하여 JSON 객체의 속성 값을 출력 -->
<script>
$(function() {
	 $("#checkJson").click(function() {	      
		 
		 // members 배열에 회원 정보를 객체의 name-value 쌍으로 저장함
	    var jsonStr = '{"members":[{"name":"박지성","age":"25","gender":"남자","nickname":"날센돌이"}'
	    	           +', {"name":"손흥민","age":"30","gender":"남자","nickname":"탱크"}] }';
	    var jsonInfo = JSON.parse(jsonStr);
	    var output ="회원 정보<br>";
	    output += "=======<br>";
	    
	    // jsonInfo의 members[i] 속성의 name, age 등 프로퍼티로 접근해야 함
	    for(var i in jsonInfo.members){
           output += "이름: " + jsonInfo.members[i].name+"<br>";
	       output += "나이: " + jsonInfo.members[i].age+"<br>";
	       output += "성별: " + jsonInfo.members[i].gender+"<br>";
	       output += "별명: " +jsonInfo.members[i].nickname+"<br><br><br>";
	    }
	    $("#output").html(output);
	 });
     });
</script>
</head>
<body>
    <a id="checkJson" style="cursor:pointer">출력</a><br><br>
    <div id="output"></div>
</body>
</html>
