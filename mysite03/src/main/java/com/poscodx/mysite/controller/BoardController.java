package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.poscodx.mysite.vo.UserVo;

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
	
	@RequestMapping(value="/writeform/{no}/{pageNum}", method=RequestMethod.GET) // write form
	public String write(@PathVariable("no") String no, @PathVariable("pageNum") String pageNum,
			HttpSession session, Model model) {
		
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/noSession";
		}
		
		return "board/write";
	}

	@RequestMapping(value="/write/{no}/{pageNum}", method=RequestMethod.POST) // write 
	public String write(@PathVariable("no") Long no, @PathVariable("pageNum") String pageNum,
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="content", required=true, defaultValue="") String content,
			HttpSession session, Model model) {
		
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/noSession";
		}
		
		BoardVo vo = boardService.getBoardVoForWrite(no, authUser, title, content);
		boardService.insert(vo);
		
		return "redirect:/board/" + pageNum;	
	}
	
	@RequestMapping(value="/viewform/{no}/{pageNum}", method=RequestMethod.GET) // view form
	public String write(@PathVariable("no") Long no, @PathVariable("pageNum") String pageNum, 
			@RequestParam(value="title", required=true, defaultValue="") String title,
			@RequestParam(value="content", required=true, defaultValue="") String content,
			Model model) {
		
		BoardVo boardVo = boardService.getPost(no);
		model.addAttribute("vo", boardVo);
		
		return "board/view";
	}
	
	@RequestMapping(value="/deleteform/{no}/{pageNum}", method=RequestMethod.GET) // delete form
	public String delete(@PathVariable("no") Long no, @PathVariable("pageNum") String pageNum, 
			Model model, HttpSession session) {
		
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/noSession";
		}

		return "board/delete";
	}

	@RequestMapping(value="/delete/{no}/{pageNum}", method=RequestMethod.POST) // delete action
	public String delete(@PathVariable("no") Long no, @PathVariable("pageNum") String pageNum,
			@RequestParam(value="password", required=true, defaultValue="") String password,
			HttpSession session) {
		
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/noSession";
		}
				
		boolean result = boardService.deletePost(no, password);
	
		if(result) { // 비밀번호 일치 - 삭제 성공 
			return "redirect:/board/" + pageNum;
		} else { // 비밀번호불일치 - 삭제 실패 
			return "redirect:/board/deleteform/" + no + "/" + pageNum;
		}	
	}
	
	@RequestMapping("/modifyform/{no}/{pageNum}")
	public String update(HttpSession session, Model model,
			@PathVariable("no") Long no, @PathVariable("pageNum") String pageNum) {
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/noSession";
		}
		///////////////////////////////////////////////////
		
		BoardVo boardVo = boardService.getPost(no); 
		model.addAttribute("vo", boardVo);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}/{pageNum}", method=RequestMethod.POST)
	public String update(HttpSession session, BoardVo boardVo,
			@PathVariable("no") Long no, @PathVariable("pageNum") String pageNum) {
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/noSession";
		}
		///////////////////////////////////////////////////
		
		boardService.update(no, boardVo);
		
		return "redirect:/board/viewform/" + no + "/" + pageNum;
	}
}
