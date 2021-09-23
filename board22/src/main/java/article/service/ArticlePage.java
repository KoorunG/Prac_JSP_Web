package article.service;

import java.util.List;

import article.model.Article;

/* 앞에서 전체 게시글 개수와 원하는 범위의 게시글 목록을 읽어오는 기능을 추가했으므로, 이제 게시글 목록을 제공하는 서비스 클래스를 구현한다
 * 먼저 게시글 데이터와 페이징 관련 정보를 담을 ArticlePage 클래스를 구현한다
 * */
public class ArticlePage {

	private int total;					// 전체 게시글의 개수 보관
	private int currentPage;			// 사용자가 요청한 페이지 번호를 보관
	private List<Article> content;		// 화면에 출력할 게시글 목록을 보관 
	private int totalPages;				// 전체 페이지 개수 보관
	private int startPage;				// 페이징 처리 부분의 페이지 이동 링크의 시작번호를 저장한다
	private int endPage;				// 페이징 처리 부분의 페이지 이동 링크의 끝 번호를 저장한다
	
	public ArticlePage(int total, int currentPage, int size, List<Article> content) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		
		if(total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
			
			// total != 0이면
		} else {
			
			// 전체 개시글 개수 (total)을 size로 나눈 값이 페이지 개수로 사용된다
			totalPages = total / size;
			
			// size와 딱 나누어 떨어지지 않았을 경우!
			// totalPages를 1 늘린다 (게시글 목록이 짤리면 안되므로)
			if(total % size > 0) {
				totalPages++;
			}
			
			// 화면 하단에 보여줄 페이지 이동 링크(페이징 처리 부분)의 시작 페이지 번호를 구한다
			// 화면 하단에 [1,2,3,4,5]나 [6,7,8,9,10] 처럼 5개 페이지씩 이동 링크를 출력한다고 가정하면
			// 현재 페이지가 3이면 시작 페이지가 1이 되고, 현재 페이지가 10이면 시작 페이지는 6이 된다
			int modVal = currentPage % 5;
			startPage = currentPage / 5 * 5 + 1;
			if(modVal == 0) startPage -= 5;
			
			// 화면 하단에 보여줄 페이지 이동링크의 끝 페이지 번호를 구함
			// 화면 하단에 5개씩 이동 링크를 출력하므로 시작 페이지 번호에 4를 추가한 값이 끝 페이지 번호임
			endPage = startPage + 4;
			if(endPage > totalPages) endPage = totalPages;
		}
	}

	public int getTotal() {
		return total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<Article> getContent() {
		return content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	
	public boolean hasNoArticles() {
		return total == 0;
	}
	
	public boolean hasArticles() {
		return total > 0;
	}
	
	
	
}
