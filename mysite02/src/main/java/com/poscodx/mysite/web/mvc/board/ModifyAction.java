package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		
		// 세션 만료 
		if(userVo == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=noSession");
		} else {
			long no = Long.parseLong(request.getParameter("no"));
			String title = request.getParameter("title");
			String contents = request.getParameter("content");
			String pageNum = request.getParameter("pageNum");
			
			BoardVo vo = new BoardVo();
			vo.setNo(no);
			vo.setTitle(title);
			vo.setContents(contents);
			
			new BoardDao().update(vo);
			response.sendRedirect(request.getContextPath() + "/board?a=viewform&no=" + no + "&pageNum=" + pageNum);
	
		}
	}
}