package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.mysite.vo.PageVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;


public class ListAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int curPageNo = Integer.parseInt(request.getParameter("pageNum")); // 현재 페이지 no
		int totalPost = new BoardDao().getTotalPost(); // 총 게시글 개수 
		// int postSize = 2; // test
		int postSize = 5; // 한 페이지당 보여지는 게시글 개수 
		int pageSize = 5; // 보여지는 페이지(페이지 네비게이션) 개수 
		
		int totalPageNo = (totalPost - 1) / postSize + 1; // 페이지 총 개수(마지막으로 보여질 페이지 no)
		int endPageNo = (int) (Math.ceil((double)curPageNo / pageSize) * pageSize); // 보이는 끝 페이지 
		int startPageNo = endPageNo - pageSize + 1; // 보이는 첫 페이지 
		System.out.println("curPage : " + curPageNo + ", start : " + startPageNo + ", end : " + endPageNo);
		
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
		
		System.out.println("pageVo : " + pageVo);
		
		List<BoardVo> pagePostList = new BoardDao().pagePostList(pageVo);
		System.out.println("pagePostList : " + pagePostList);
		
		
		
		List<BoardVo> list = new BoardDao().findAll();
		// System.out.println("ListAction : " + list);
		request.setAttribute("list", list);
		request.setAttribute("pageInfo", pageVo);
		request.setAttribute("pagePostList", pagePostList);
		
		WebUtil.forward("board/list", request, response);
	}
}