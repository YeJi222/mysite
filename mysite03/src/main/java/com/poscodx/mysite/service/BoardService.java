package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public PageVo getPageVo(int curPageNo) {
		int totalPost = boardRepository.getTotalPost(); // 총 게시글 개수 
		int postSize = 8; // 한 페이지당 보여지는 게시글 개수 
		int pageSize = 5; // 보여지는 페이지(페이지 네비게이션) 개수 
		
		int totalPageNo = (totalPost - 1) / postSize + 1; // 페이지 총 개수(마지막으로 보여질 페이지 no)
		int endPageNo = (int) (Math.ceil((double)curPageNo / pageSize) * pageSize); // 보이는 끝 페이지 
		int startPageNo = endPageNo - pageSize + 1; // 보이는 첫 페이지 
		
		boolean prevBtn = true;
		boolean nextBtn = true;
		if(startPageNo == 1) { // startPageNo가 1부터 시작하면 이전 버튼 안보이게 
			prevBtn = false;
		}
		if(totalPageNo <= endPageNo) { // totalPageNo가 끝이면 다음 버튼 안보이게 
			nextBtn = false; 
		}
		
		PageVo pageVo = 
				new PageVo(curPageNo, totalPost, postSize, pageSize, totalPageNo, endPageNo, startPageNo, prevBtn, nextBtn);
		
		return pageVo;
	}

	public List<BoardVo> getPostList(PageVo pageVo) {
		return boardRepository.pagePostList(pageVo);
	}

	public boolean deletePost(Long no, String password) {
		return boardRepository.deleteByNoAndPassword(no, password);
	}
	
	
	
	
	
}
