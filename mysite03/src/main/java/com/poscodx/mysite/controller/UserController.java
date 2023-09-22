package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/////////// join ///////////
	@RequestMapping(value="/join", method=RequestMethod.GET) 
	public String join() { // join form
		return "user/join"; 
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST) 
	public String join(@Valid UserVo userVo, BindingResult result, Model model) { // join action
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		userService.join(userVo);
		
		return "redirect:/user/joinsuccess"; 
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET) 
	public String joinsuccess() { // joinsuccess form
		return "user/joinsuccess"; 
	}
	
	/////////// login ///////////
	@RequestMapping(value="/login", method=RequestMethod.GET) 
	public String login() { // login form
		return "user/login"; 
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {		
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo", userVo);
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {
		userVo.setNo(authUser.getNo());
		userService.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String handlerException() {
//		return "error/exception";
//	}
	
//	@RequestMapping(value="/noSession", method=RequestMethod.GET)
//	public String noSession() {
//		return "user/noSession";
//	}
}
