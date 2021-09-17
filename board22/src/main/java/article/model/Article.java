package article.model;

import java.util.Date;

public class Article {
	
	// article 테이블의 article_no 칼럼과 연관됨 (PK)
	private Integer number;
	
	// 작성자정보를 보관하기 위해 앞서 구현한 Writer 타입의 필드를 정의함
	private Writer writer;
	

	private String title;
	private Date regDate;
	private Date modifiedDate;
	private int readCount;
	
	public Article(Integer number, Writer writer, String title, Date regDate, Date modifiedDate, int readCount) {
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
	}

	public Integer getNumber() {
		return number;
	}

	public Writer getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public int getReadCount() {
		return readCount;
	}
	
	
	
	
}
