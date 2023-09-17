package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/loginform", method=RequestMethod.GET) // login form
	public String login() {
		return "user/loginform";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST) // login action
	public String login(Model model,
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		boolean result = userService.loginAction(email, password);
	
		if(result) { // 로그인 성공 
			return "redirect:/";
		} else { // 로그인 실패 
			model.addAttribute("email", email);
			return "redirect:/user/loginform";
		}	
	}

	/*
	@RequestMapping({"", "/"})
	public String main(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	@RequestMapping("add")
	public String add(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping(value="/deleteform/{no}", method=RequestMethod.GET) // delete form
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/deleteform";
	}

	
	*/
}
