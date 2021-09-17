package article.model;

public class ArticleContent {

	// article 테이블의 article_no 칼럼과 연관 (FK)
	private Integer number;	
	private String content;

	public ArticleContent(Integer number, String content) {
		this.number = number;
		this.content = content;
	}

	public Integer getNumber() {
		return number;
	}

	public String getContent() {
		return content;
	}
	
}
