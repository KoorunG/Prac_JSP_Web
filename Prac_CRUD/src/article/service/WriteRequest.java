package article.service;

import java.util.Map;


public class WriteRequest {
	
	private String title;		
	private String content;		
	
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
		
		
		if(title == null || title.trim().isEmpty()) {
			
			errors.put(("article_title"), Boolean.TRUE);
		}
	}
}
