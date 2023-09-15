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
		String pageNum = request.getParameter("pageNum");
		Long no = Long.parseLong(str_no);
		String password = request.getParameter("password");
		
		System.out.println("pageNum : " + pageNum);
		
		boolean result = new BoardDao().deleteByNoAndPassword(no, password);
		System.out.println("delete result : " + result);
		if(result) {
			System.out.println("삭제 완료");
			response.sendRedirect(request.getContextPath() + "/board?pageNum=" + pageNum);
		} else {
			System.out.println("비밀번호 틀렸습니다");
			response.sendRedirect(request.getContextPath() + "/board?a=deleteform&no=" + str_no + "&pageNum=" + pageNum);
		}
		
	}
}