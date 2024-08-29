package com.market.post;

import lombok.Getter;

@Getter
public class SetPage {
	private Integer totalPosts;
	private int postsPerPage;
	private int totalPages;
	private int displayPageNum;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int limitStart;
	
	public SetPage(Integer currentPage, Integer totalPosts) {
		
		this.totalPosts = totalPosts;
		
		this.postsPerPage = 8;
		
		this.totalPages = totalPosts / postsPerPage;
		if (totalPosts % postsPerPage > 0 || totalPosts == 0) {
			totalPages++;
		}
		
		this.displayPageNum = 10;
		
		this.startPage = ((currentPage - 1) / displayPageNum) * displayPageNum + 1;
		
		this.endPage = (((currentPage - 1) / displayPageNum) + 1) * displayPageNum;
		if (endPage > totalPages) {
			endPage = totalPages;
		}
		
		this.prev = (currentPage == 1) ? false : true;
		
		this.next = (currentPage == endPage) ? false : true;
		
		this.limitStart = ((currentPage - 1) * postsPerPage);
	}
	
	public boolean getPrev() {
		return this.prev;
	}
	
	public boolean getNext() {
		return this.next;
	}
}
