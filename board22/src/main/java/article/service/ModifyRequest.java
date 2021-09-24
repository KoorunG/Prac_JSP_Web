package article.service;

import java.util.Map;

// 게시글을 수정하려면 수정할 게시글의 번호, 수정자의 아이디
// 수정할 제목, 수정할 데이터가 필요하다
public class ModifyRequest {
	
	private String userId;
	private int articleNumber;
	private String title;
	private String content;
	
	public ModifyRequest(String userId, int articleNumber, String title, String content) {
		this.userId = userId;
		this.articleNumber = articleNumber;
		this.title = title;
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	// 검증 메소드
	public void validate(Map<String, Boolean> errors) {
		if(title == null || title.trim().isEmpty()) {
			errors.put("title", Boolean.TRUE);
		}
	}
	
}
