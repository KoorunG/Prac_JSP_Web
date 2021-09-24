package article.service;

import java.util.Map;

// 게시글 쓰기에 필요한 데이터를 제공함

public class WriteRequest {
	
	private String title;		// 제목
	private String content;		// 내용
	
	public WriteRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public void validate(Map<String, Boolean> errors) {
		
		// title이 null이거나 빈 값이라면
		
		if(title == null || title.trim().isEmpty()) {
			
			// errors Map에 "title"으로 error를 등록한다
			errors.put(("article_title"), Boolean.TRUE);
		}
	}
}
