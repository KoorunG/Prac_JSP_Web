
import java.io.IOException;	
import java.util.ArrayList;	
import java.util.Collections;	
import java.util.List;	
import java.util.regex.Matcher;	
import java.util.regex.Pattern;	
import javax.websocket.OnClose;	
import javax.websocket.OnMessage;	
import javax.websocket.OnOpen;	
import javax.websocket.Session;	
import javax.websocket.server.ServerEndpoint;	
 	
// WebSocket 호스트 설정	
@ServerEndpoint("/broadsocket")	
public class BroadSocket {	
  // 접속 된 클라이언트 WebSocket session 관리 리스트	
  private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());	
  // 20행 : 메시지에서 유저 명을 취득하기 위한 정규식 표현
  private static Pattern pattern = Pattern.compile(""); // "" 안에 정규식 넣으면 됨
  // 자바에서 \를 표현하려면 \\처럼 사용해야 합니다.
  // ^는 문자열의 시작지점을 찾습니다. 따라서 ^ 다음으로 오는 패턴으로 문자열이 시작되는 것을 찾습니다.
  // 즉, ^regex 는 ^ 다음 regex로 line을 시작하는지 의미합니다.
  // {X}는	X회 이상 반복함을 의미합니다. 참고로, {X,Y} 는 X~Y 사이의 수만큼 반복함을 의미합니다.
  // . 는 어떤 문자 1개를 의미합니다.
  // *? 는 가장 적게 일치하는 패턴을 찾음
  // 참고로, *은 * 앞의 요소가 0이상 반복되는 것을 의미합니다.
  // 그리고, ?는 요소가 0 또는 1회만 반복되는 것을 의미합니다.
  
  // WebSocket으로 브라우저가 접속하면 요청되는 함수		
  @OnOpen	
  public void handleOpen(Session userSession) {	
    // 클라이언트가 접속하면 WebSocket세션을 리스트에 저장한다.	
    sessionUsers.add(userSession);	
    // 콘솔에 접속 로그를 출력한다.	
    System.out.println("client is now connected...");	
  }	
  // WebSocket으로 메시지가 오면 요청되는 함수	
  @OnMessage	
  public void handleMessage(String message, Session userSession) throws IOException {	
    // 메시지 내용을 콘솔에 출력한다.	
    System.out.println(message);	
    // 초기 유저 명	
    String name = "anonymous";	
    // 메시지로 유저 명을 추출한다.	
    Matcher matcher = pattern.matcher(message);	
    // 메시지 예: {{유저명}}메시지	
    if (matcher.find()) {	
      name = matcher.group();	
    }	
    // 53행 : 변수의 상수화  : replaceAll (패턴에 맞는 값을 새로운 값으로 치환)
    // replaceAll(regex, replacement)은 regex와 일치하는 내용을 replacement로 교체합니다.
    final String msg = message.replaceAll(pattern.pattern(), "");
    // 56행 : String.replaceFirst(regex, replacement) : regex와 가장 먼저 일치하는 것을 replacement로 변환 
    // $ 는 문자열의 종료를 의미합니다.
    final String username = name.replaceFirst("", "").replaceFirst("", "");  // "", "" 각각의 첫번째 "" 안에 정규식 넣으면 됨
    // session관리 리스트에서 Session을 취득한다.	
    sessionUsers.forEach(session -> {	
      // 리스트에 있는 세션과 메시지를 보낸 세션이 같으면 메시지 송신할 필요없다.	
      if (session == userSession) {	
        return;	
      }	
      try {	
        // 리스트에 있는 모든 세션(메시지 보낸 유저 제외)에 메시지를 보낸다. (형식: 유저명 => 메시지)	
        session.getBasicRemote().sendText(username + " => " + msg);	
      } catch (IOException e) {	
        // 에러가 발생하면 콘솔에 표시한다.	
        e.printStackTrace();	
      }	
    });	
  }	
  // WebSocket과 브라우저가 접속이 끊기면 요청되는 함수	
  @OnClose	
  public void handleClose(Session userSession) {	
    // session 리스트로 접속 끊은 세션을 제거한다.	
    sessionUsers.remove(userSession);	
    // 콘솔에 접속 끊김 로그를 출력한다.	
    System.out.println("client is now disconnected...");	
  }	
  
  
  // WebSocket 리스너와 구조가 비슷하지만 각 리스너에 Session 파라미터를 추가함
  // Session은 WebSocket의 커넥션 정보가 들어있는 객체임
}
