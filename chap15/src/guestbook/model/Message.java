package guestbook.model;

public class Message {
	
	private int id;
	private String guestName;
	private String password;
	private String message;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean hasPassword() {
		return password != null && !password.isEmpty();
	}
	// 패스워드가 비어있는지 검사
	
	public boolean matchPassword(String pwd) {
		return password != null && password.equals(pwd);
	}
	// 패스워드가 일치하는지 검사
}
