package guestbook.service;

import java.util.List;

import guestbook.model.Message;

public class MessageListView {
	
	// 메세지 목록을 보여주는 파일 (일종의 Service)
	
	private int messageTotalCount;
	private int currentPageNumber;
	private List<Message> messageList;
	private int pageTotalCount;
	private int messageCountPerPage;
	private int firstRow;
	private int endRow;
	
	public MessageListView(List<Message> messageList, int messageTotalCount, int currentPageNumber, 
			int messageCountPerPage, int firstRow, int endRow) {
		this.messageTotalCount = messageTotalCount;
		this.currentPageNumber = currentPageNumber;
		this.messageList = messageList;
		this.messageCountPerPage = messageCountPerPage;
		this.firstRow = firstRow;
		this.endRow = endRow;
		
		calculatePageTotalCount();
	}

	private void calculatePageTotalCount() {
		if(messageTotalCount == 0) {
			pageTotalCount = 0;
		} else {
			pageTotalCount = messageTotalCount / messageCountPerPage;
			if(messageTotalCount % messageCountPerPage > 0) {
				pageTotalCount++;
			}
		}
	}

	public int getMessageTotalCount() {
		return messageTotalCount;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public int getPageTotalCount() {
		return pageTotalCount;
	}

	public int getMessageCountPerPage() {
		return messageCountPerPage;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public int getEndRow() {
		return endRow;
	}
	
	public boolean isEmpty() {
		return messageTotalCount == 0;
	}
	
	
}
