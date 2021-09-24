package article.model;

public class Article {
	
	// article 테이블의 article_no 칼럼과 연관됨 (PK)
	private String title;
	private String content;
	
	
	public Article(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
}
