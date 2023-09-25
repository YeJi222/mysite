package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.GalleryService;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index() {
		return "gallery/index";
	}
	
	@Auth(Role="ADMIN")
	@RequestMapping("/upload")
	public String upload() {
		
		
		return "redirect:/gallery";
	}
	
	// delete 
	// @Auth(Role="ADMIN")
	// @RequestMapping("")
	
}
