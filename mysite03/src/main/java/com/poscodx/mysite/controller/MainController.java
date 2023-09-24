package com.poscodx.mysite.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Controller
public class MainController {
	
	@RequestMapping("")
	public String index(Model model, HttpServletRequest request) {
		return "main/index";
	}
}