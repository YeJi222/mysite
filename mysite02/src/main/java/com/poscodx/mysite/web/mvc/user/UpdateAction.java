package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// 세션 실패  
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		Long no = authUser.getNo();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		UserVo userVo = new UserVo();
		userVo.setNo(no);
		userVo.setName(name);
		userVo.setPassword(password);
		userVo.setGender(gender);
		
		UserVo updateUserVo = new UserDao().updateUserInfo(userVo);
		
		/* session update */
		session.setAttribute("authUser", updateUserVo);
		
		response.sendRedirect(request.getContextPath() + "/user?a=updateform");
	}

}