<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String idValue = request.getParameter("id");	// 쿼리스트링으로 입력받은 id를 idValue 변수에 넣음

	Connection conn = null;
	PreparedStatement pstmtItem = null;
	PreparedStatement pstmtDetail = null;			// preparedStatement 두개 준비

	String jdbcDriver =  "jdbc:mysql://localhost:3306/chap14" 	
					   + "?characterEncoding=UTF-8" 			// 인코딩 정보
					   + "&serverTimezone=UTC"					// 
					   + "&useSSL=false" 
					   + "&allowpublickeyretrieval=true";

		String dbUser = "jspexam";
		String dbPass = "jsppw";
			
		
		// 예외 발생 시 처리를 위해 Throwable 객체 참조
		// occuredException 변수를 null값으로 초기화함!
		
		Throwable occuredException = null;		// Throwable형의 변수 occuredException 선언 및 초기화
		
		try {
			int id = Integer.parseInt(idValue);	// 위에서 입력받은 파라미터를 int로 형변환 후 변수 id에 넣음
			
			// 커넥션 생성
			conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
			
			// 트랜잭션 처리의 commit, rollback 활용을 위해 autocommit을 false로 설정!
			conn.setAutoCommit(false);
			
			// ITEM 테이블에 데이터를 삽입하는 첫번째 쿼리문을 실행한다
			pstmtItem = conn.prepareStatement("insert into item values(?, ?)");
			pstmtItem.setInt(1,id);
			pstmtItem.setString(2,"상품 이름 " + id);
			pstmtItem.executeUpdate();
			
			if(request.getParameter("error") != null) {	// 쿼리스트링에 error 으로 값이 들어오면
				throw new Exception("의도적 익셉션 발생!");	// "의도적 익셉션 발생!" 이라는 메시지 출력
			}
			
			// ITEM_DETAIL 테이블에 데이터를 삽입하는 두번째 쿼리문을 실행한다
			pstmtDetail = conn.prepareStatement("insert into item_detail values(?,?)");
			pstmtDetail.setInt(1,id);
			pstmtDetail.setString(2,"상세 설명 " + id);
			pstmtDetail.executeUpdate();
			
			// 쿼리문이 모두 실행되었다면 commit() 하는것으로 트랜잭션 종료
			conn.commit();
			
		} catch(Throwable e){		// 어떤 예외가 발생하였다면
			if(conn != null) {		
				try {
					conn.rollback();// 롤백처리를 해줌
				} catch(SQLException ex) {
					
				}
			}
			occuredException = e;	// 그리고 발생한 예외를 occuredException 변수에 집어넣음
			
		} finally {					// 오류에 상관없이 PreparedStatement, Connection을 닫아서 리소스 낭비 방지
			if(pstmtItem != null){
				try {
					pstmtItem.close();
				} catch(SQLException ex) {
				
				}
			}
			
			if(pstmtDetail != null){
				try {
					pstmtDetail.close();
				} catch(SQLException ex) {
				
				}
			}
			
			if(conn != null){
				try {
					conn.close();
				} catch(SQLException ex) {
				
				}
			}
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ITEM 값 입력</title>
</head>
<body>
	<% if(occuredException != null) { %>
	에러가 발생하셨습니다 : <%= occuredException.getMessage() %>	<!-- 위에서 캐치한 occuredException에서 메세지를 출력함 -->
	<% } else { %>
	데이터가 성공적으로 들어갔습니다									<!-- 만일 exception이 없다면 성공 메세지 출력 -->
	<% } %>
</body>
</html>