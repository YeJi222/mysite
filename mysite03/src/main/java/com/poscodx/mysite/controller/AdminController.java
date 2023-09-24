package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Auth(Role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String update(SiteVo vo, @RequestParam("file") MultipartFile file) {
		// System.out.println("before url : " + vo.getProfile());
		
		/* 이미지 파일 업로드 처리 */
		String url = fileUploadService.restore(file);

		// siteVo profile 셋 해주기 
		if(url == null) { // before url로 세팅 
			url = vo.getProfile();
		}
		vo.setProfile(url);
		System.out.println("admin controller vo" + vo);
		
		siteService.updateSite(vo);
		
		// 수정된 SiteVo를 다시 ApplicationContext에 등록
		((ConfigurableApplicationContext) applicationContext).getBeanFactory().registerSingleton("siteVo", vo);
        // applicationContext.getBeanFactory().registerSingleton("siteVo", vo);
		
		// 빈이 정상적으로 등록되었는지 확인
		SiteVo registeredSiteVo = applicationContext.getBean("siteVo", SiteVo.class);

		// 확인
		System.out.println("Registered SiteVo: " + registeredSiteVo);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}	
}