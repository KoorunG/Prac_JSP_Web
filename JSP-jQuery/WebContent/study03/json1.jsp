<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSON 테스트</title>
  <!-- JSON 자료형을 실제로 사용하여 값을 출력하는 예제를 실습 -->
  <!-- JSON 배열에 문자열을 저장한 후 웹페이지에 출력하는 내용의 웹 프로그램 -->
  <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
    $(function() {
    	
    	// 이름을 저장하는 JSON 배열을 name으로 선언합니다 
        $("#checkJson").click(function() {
        var jsonStr  = '{"name": ["홍길동", "이순신", "임꺽정"] }';          
        // 제이쿼리의 JSON 기능인 parse() 메소드를 이용하여 JSON 자료형을 가져옴
        var jsonInfo = JSON.parse(jsonStr);
        var output ="회원 이름<br>";
        output += "=======<br>";
        
        // 파싱한 데이터의 이름값을 iterator으로 돌면서 가져옴
        for(var i in jsonInfo.name) {
            output += jsonInfo.name[i]+"<br>";
        }
        
        // <div id="output">에 html 태그를 붙임
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
