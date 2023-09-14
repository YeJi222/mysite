package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		String no = request.getParameter("no");
		
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		Long user_no = userVo.getNo();
		String writer = userVo.getName();
		
		// 새 글일 때 
		BoardVo vo = new BoardVo();
		if(no.isEmpty()) { // 새 글일 때 
			vo.setNo(null);
		} else { // 새 글이 아닐 때 
			vo.setNo(Long.parseLong(no));
			
			BoardVo boardVo = (BoardVo)new BoardDao().getInfoByNo(Long.parseLong(no));
			vo.setHit(boardVo.getHit());
			vo.setG_no(boardVo.getG_no());
			vo.setO_no(boardVo.getO_no());
			vo.setDepth(boardVo.getDepth());
		}
		vo.setUser_no(user_no);
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setWriter(writer);
		
		// System.out.println("writeAction : " + vo);
		
		new BoardDao().insert(vo);
		response.sendRedirect(request.getContextPath() + "/board?pageNum=1");
	}
}