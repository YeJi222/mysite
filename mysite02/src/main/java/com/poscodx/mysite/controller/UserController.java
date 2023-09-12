package com.poscodx.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.mysite.web.mvc.user.JoinAction;
import com.poscodx.mysite.web.mvc.user.JoinFormAction;
import com.poscodx.mysite.web.mvc.user.JoinSuccessAction;
import com.poscodx.mysite.web.mvc.user.UserActionFactory;
import com.poscodx.web.mvc.Action;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter("a");
		Action action = new UserActionFactory().getAction(actionName); 
		action.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
