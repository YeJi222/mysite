package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str_no = request.getParameter("no");
		String pageNum = request.getParameter("pageNum");
		Long no = Long.parseLong(str_no);
		String password = request.getParameter("password");
		
		boolean result = new BoardDao().deleteByNoAndPassword(no, password);
		if(result) {
			// 삭제 완료
			response.sendRedirect(request.getContextPath() + "/board?pageNum=" + pageNum);
		} else {
			// 비밀번호 틀린 경우 
			response.sendRedirect(request.getContextPath() + "/board?a=deleteform&no=" + str_no + "&pageNum=" + pageNum);
		}
	}
}