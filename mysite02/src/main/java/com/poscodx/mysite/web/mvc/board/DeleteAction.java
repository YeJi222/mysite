package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str_no = request.getParameter("no");
		Long no = Long.parseLong(str_no);
		String password = request.getParameter("password");
		
		new BoardDao().deleteByNoAndPassword(no, password);
		response.sendRedirect(request.getContextPath() + "/board");
	}
}