package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("/{pageNum}")
	public String main(@PathVariable("pageNum") int pageNum, Model model) {
		
		PageVo pageVo = boardService.getPageVo(pageNum);
		model.addAttribute("pageInfo", pageVo);
		
		List<BoardVo> pagePostList = boardService.getPostList(pageVo);
		model.addAttribute("list", pagePostList);
		
		return "board/list";
	}
}
