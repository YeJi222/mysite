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
	public String join(UserVo userVo) { // join action
		System.out.println(userVo);
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
	
	@RequestMapping(value="/login", method=RequestMethod.POST) // login action
	public String login(Model model, HttpSession session, 
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		
		UserVo authUser = userService.getUser(email, password);
		if(authUser == null) { // 로그인 실패 
			model.addAttribute("email", email);
			return "user/login";
		}
	
		/* 인증 처리 */
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}

	/////////// logout ///////////
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping("/update")
	public String update(HttpSession session, Model model) {
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////
		
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo", userVo);
		
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, UserVo userVo) {
		// Access Control(접근 제어) 
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////////
		userVo.setNo(authUser.getNo());
		userService.update(userVo);
		
		authUser.setName(userVo.getName());
		
		return "redirect:/user/update";
	}
}
