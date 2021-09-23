package article.service;

import article.model.Article;
import article.model.ArticleContent;

// ArticleData 클래스는 ReadArticleService 클래스에 구현할 getArticle() 메소드의 리턴타입으로
// Article 객체와 ArticleContent 객체를 한 객체에 담기 위한 용도로 사용한다!

public class ArticleData {
	
	private Article article;
	private ArticleContent content;
	
	public ArticleData(Article article, ArticleContent content) {
		this.article = article;
		this.content = content;
	}

	public Article getArticle() {
		return article;
	}

	public String getContent() {
		return content.getContent();
	}
	
	
}
