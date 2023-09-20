package com.poscodx.mysite.vo;

public class PageVo {
	private int curPageNo;
	private int totalPost;
	private int postSize;
	private int pageSize;
	private int totalPageNo;
	private int endPageNo;
	private int startPageNo;
	private boolean prevBtn;
	private boolean nextBtn;
	
	public PageVo() {
	}

	public PageVo(int curPageNo, int totalPost, int postSize, int pageSize, int totalPageNo, int endPageNo,
			int startPageNo, boolean prevBtn, boolean nextBtn) {
		this.curPageNo = curPageNo;
		this.totalPost = totalPost;
		this.postSize = postSize;
		this.pageSize = pageSize;
		this.totalPageNo = totalPageNo;
		this.endPageNo = endPageNo;
		this.startPageNo = startPageNo;
		this.prevBtn = prevBtn;
		this.nextBtn = nextBtn;
	}
	
	public int getCurPageNo() {
		return curPageNo;
	}
	public void setCurPageNo(int curPageNo) {
		this.curPageNo = curPageNo;
	}
	public int getTotalPost() {
		return totalPost;
	}
	public void setTotalPost(int totalPost) {
		this.totalPost = totalPost;
	}
	public int getPostSize() {
		return postSize;
	}
	public void setPostSize(int postSize) {
		this.postSize = postSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPageNo() {
		return totalPageNo;
	}
	public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}
	public int getEndPageNo() {
		return endPageNo;
	}
	public void setEndPageNo(int endPageNo) {
		this.endPageNo = endPageNo;
	}
	public int getStartPageNo() {
		return startPageNo;
	}
	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}
	public boolean isPrevBtn() {
		return prevBtn;
	}
	public void setPrevBtn(boolean prevBtn) {
		this.prevBtn = prevBtn;
	}
	public boolean isNextBtn() {
		return nextBtn;
	}
	public void setNextBtn(boolean nextBtn) {
		this.nextBtn = nextBtn;
	}
	
	@Override
	public String toString() {
		return "PageVo [curPageNo=" + curPageNo + ", totalPost=" + totalPost + ", postSize=" + postSize + ", pageSize="
				+ pageSize + ", totalPageNo=" + totalPageNo + ", endPageNo=" + endPageNo + ", startPageNo="
				+ startPageNo + ", prevBtn=" + prevBtn + ", nextBtn=" + nextBtn + "]";
	}
		
}
