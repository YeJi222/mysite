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
import com.poscodx.web.utils.WebUtil;

public class ModifyFormAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		// 세션 만료 
		if(userVo == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=noSession");
		} else {
			long no = Long.parseLong(request.getParameter("no"));
			
			BoardVo vo = new BoardDao().getInfoByNo(no);
			request.setAttribute("vo", vo);
			
			WebUtil.forward("board/modify", request, response);
		}
	}
}