package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getImages();
		model.addAttribute("list", list);
		
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
	@Auth(Role="ADMIN")
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET) // delete action
	public String delete(@PathVariable("no") Long no) {
		galleryService.deleteImage(no);
	
		return "redirect:/gallery";	
	}
}
