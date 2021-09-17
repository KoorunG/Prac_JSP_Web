package member.model;

import java.util.Date;

public class Member {
	
	private String id;
	private String name;
	private String password;
	private Date regDate;
	
	public Member(String id, String name, String password, Date regDate) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public Date getRegDate() {
		return regDate;
	}
	
	// 암호가 맞는지 여부를 검사하는 메소드 
	// 추후 암호 변경 기능을 구현할 때 사용
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
	
	// 암호 변경 메소드
	// 파라미터로 입력받은 값을 기존 패스워드에 (this.password)에 넣는 것으로 구현함
	public void changePassword(String newPwd) {
		this.password = newPwd;
	}
	
}
