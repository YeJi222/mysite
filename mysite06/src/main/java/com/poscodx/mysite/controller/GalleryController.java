package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.GalleryService;
import com.poscodx.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private FileUploadService fileUploadService;
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index() {
		return "gallery/index";
	}
	
	@Auth(Role="ADMIN")
	@RequestMapping("/upload")
	public String upload(GalleryVo vo, @RequestParam("file") MultipartFile file) {
		/* 이미지 파일 업로드 처리 */
		String url = fileUploadService.restore(file);

		// image url 셋 해주기 
		if(url == null) { // before url로 세팅 
			url = vo.getImage_url();
		}
		
		vo.setImage_url(url);
		// System.out.println("vo : " + vo);
		
		// upload gallery image
		galleryService.addImage(vo);
		
		return "redirect:/gallery";
	}
	
	// delete 
	// @Auth(Role="ADMIN")
	// @RequestMapping("")
	
}
