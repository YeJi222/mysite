package com.poscodx.mysite.controller;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.GuestbookVo;
import com.poscodx.mysite.vo.SiteVo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
//	@Autowired
//	private SiteService siteService;
	
	@Autowired
    private ApplicationContext applicationContext;

	@RequestMapping("")
	public String main(Model model, HttpServletRequest request) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		
//		SiteVo vo = siteService.getSite();
//		model.addAttribute("siteVo", vo);
		
		// ApplicationContext - SiteVo 객체 가져오기
		SiteVo siteVo = (SiteVo) request.getAttribute("siteVo");
		System.out.println("guest controller : " + siteVo);
	    model.addAttribute("siteVo", siteVo);
       
//		SiteVo siteVo = applicationContext.getBean("siteVo", SiteVo.class);
//        System.out.println("guest controller : " + siteVo);
//        model.addAttribute("siteVo", siteVo);
		
        return "guestbook/main";
	}
	
	@RequestMapping("add")
	public String add(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping(value="/deleteform/{no}", method=RequestMethod.GET) // delete form
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}

	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST) // delete action
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		boolean result = guestbookService.deleteContents(no, password);
	
		if(result) { // 비밀번호 일치 - 삭제 성공 
			return "redirect:/guestbook";
		} else { // 비밀번호불일치 - 삭제 실패 
			return "redirect:/guestbook/deleteform/" + no;
		}	
	}
}
