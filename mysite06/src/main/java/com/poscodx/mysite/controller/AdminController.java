package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
		
		/* 이미지 파일 업로드 처리 */
		String url = fileUploadService.restore(file);

		// siteVo profile 셋 해주기 
		if(url == null) { // before url로 세팅 
			url = vo.getProfile();
		}
		vo.setProfile(url);
		System.out.println("admin controller vo" + vo);
		
		/* 내 코드 - 빈 불러오기 
		SiteVo existingSiteVo = applicationContext.getBean("siteVo", SiteVo.class);
		*/
		
		// 강사님 코드 
		SiteVo site = applicationContext.getBean(SiteVo.class);
		
		siteService.updateSite(vo);
		servletContext.setAttribute("siteVo", vo);
		
		/* 내 코드  
		existingSiteVo.setTitle(vo.getTitle());
		existingSiteVo.setWelcome(vo.getWelcome());
		existingSiteVo.setProfile(vo.getProfile());
		existingSiteVo.setDescription(vo.getDescription());
		*/
		
		/* 강사님 코드 - ServletContext 주입받은 것 사용 
		servletContext.setAttribute("siteVo", vo);
		*/
		
		/* 강사님 코드 - ApplicationContext 주입받은 것 사용 */ 
		/*
		site.setTitle(vo.getTitle());
		site.setWelcome(vo.getWelcome());
		site.setProfile(vo.getProfile());
		site.setDescription(vo.getDescription());
		*/ 
		BeanUtils.copyProperties(vo, site); // 위 코드 한 줄로 대체 가능 
		
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