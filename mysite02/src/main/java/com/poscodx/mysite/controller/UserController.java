package com.poscodx.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.GuestBookVo;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("a");
		
		if("joinform".equals(action)) {
			request
				.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp")
				.forward(request, response);
		} else if("join".equals(action)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			String agreeProv = request.getParameter("agreeProv");
			
			System.out.println(name + " " + password + " " +  email + " " + gender + " " + agreeProv);
//			
//			GuestBookVo vo = new GuestBookVo();
//			vo.setName(name);
//			vo.setPassword(password);
//			vo.setContents(contents);
//			
//			new GuestBookDao().insert(vo);
//			
//			response.sendRedirect("/mysite02/main");
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
