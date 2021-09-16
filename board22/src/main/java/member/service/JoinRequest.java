package member.service;

import java.util.Map;

// JoinRequest : JoinService가 회원가입 기능을 구현할 때 필요한 요청 데이터를 담는 클래스
// MemberDao를 이용하여 실제로 회원가입 기능을 처리하는 서비스 클래스
public class JoinRequest {

	
	// 회원가입 기능을 구현할 때 필요한 요청 데이터를 보관하는 필드
	// 아이디, 이름, 암호, 암호확인
	private String id;
	private String name;
	private String password;
	private String confirmPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	// 비밀번호 확인 칸이 입력한 비밀번호와 일치하는지 검사하는 메소드
	public boolean isPasswordEqualToConfirm() {
		return password != null && password.equals(confirmPassword);
	}
	
	// 검증 메소드
	// errors 맵 객체에 데이터가 존재한다는 것은 데이터에 에러가 있다는 것을 의미한다
	// 에러가 발생하였을 시 에러화면을 보여주기보다는 JoinHandler 클래스를 이용하여 알맞은 에러 메세지를 보여주고 폼을 다시 보여주는 예외처리를 할 예정이다
	
	// ex) 아이디 값이 올바르지 않을 경우 "id" 키값으로 Boolean.TRUE 값을 추가
	
	// 즉, value메소드는 이 errors 파라미터를 JoinHandler 에서 전달받아 값이 올바르지 않으면 파라미터로 전달받은 errors 맵 객체에 (키, True) 쌍을 추가함
	public void validate(Map<String, Boolean> errors) {
		checkEmpty(errors, id, "id");
		checkEmpty(errors, name, "name");
		checkEmpty(errors, password, "password");
		checkEmpty(errors, confirmPassword, "confirmPassword");
		
		// 위에서 만든 isPassword... 메소드를 이용하여 비밀번호 확인 칸에 올바른 값이 들어갔는 지 검사
		if (!errors.containsKey("confirmPassword")) {
			if (!isPasswordEqualToConfirm()) {
				errors.put("notMatch", Boolean.TRUE);
			}
		}
	}

	// value가 공백이면 errors에 값을 넣어 에러처리함 ( fieldName이 키값 )
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if (value == null || value.isEmpty()) {
			errors.put(fieldName, Boolean.TRUE);
		}
	}
}
