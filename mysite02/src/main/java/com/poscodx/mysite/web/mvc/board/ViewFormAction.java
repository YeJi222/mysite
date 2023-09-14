package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;


public class ViewFormAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo vo = new BoardDao().getInfoByNo(no);
		new BoardDao().addHit(no);
		
		// System.out.println("viewformAction : " + vo);
		
		request.setAttribute("vo", vo);
		
		WebUtil.forward("board/view", request, response);
	}
}