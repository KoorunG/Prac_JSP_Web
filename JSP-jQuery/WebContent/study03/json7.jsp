<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  /> 
<!DOCTYPE html>
<html>
<head>
  <title>JSON 테스트</title>
  <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
  
  <!-- ajax로 JSON 데이터를 받아온 후 서블릿에서 각 배열에 대해 지정한 배열 이름으로 회원정보와 도서 정보를 가져와 출력함 -->
  <!-- 받아온 JSON 데이터를 parse() 를 이용하여 파싱한 후 이용 -->
  <script>
     $(function() {
        $("#checkJson").click(function() {
    	  $.ajax({
            type:"post",
            async:false, 
            url:"${contextPath}/json3",		// /json3 의 데이터를 ajax로 받아와서 처리함
            success:function (data,textStatus){
               var jsonInfo = JSON.parse(data);
             var memberInfo ="회원 정보<br>";
	     memberInfo += "=======<br>";
	     for(var i in jsonInfo.members){
	       memberInfo += "이름: " + jsonInfo.members[i].name+"<br>";
	       memberInfo += "나이: " + jsonInfo.members[i].age+"<br>";
	       memberInfo += "성별: " + jsonInfo.members[i].gender+"<br>";
	       memberInfo += "별명: " + jsonInfo.members[i].nickname+"<br><br>";
	     }
	        	
	     var booksInfo = "<br><br>도서 정보<br>";
	     booksInfo += "===========<br>";
	     for(var i in jsonInfo.books) {
	        booksInfo += "제목: " + jsonInfo.books[i].title+"<br>";
	        booksInfo += "저자: " + jsonInfo.books[i].writer+"<br>";
	        booksInfo += "가격: " + jsonInfo.books[i].price+"원 <br>";
	        booksInfo += "장르: " + jsonInfo.books[i].genre+"<br>";
            imageURL = jsonInfo.books[i].image;
            booksInfo += "<img src="+imageURL+" />"+"<br><br><br>";
	     }
	     $("#output").html(memberInfo+"<br>"+booksInfo);
	   },
	   error:function(data,textStatus){
	      alert("에러가 발생했습니다.");ㅣ
	   }
	  });  //end ajax	
        });
     });
</script>
</head>
<body>
   <a id="checkJson" style="cursor:pointer">데이터 수신하기</a><br><br>
    <div id="output"></div>
</body>
</html>