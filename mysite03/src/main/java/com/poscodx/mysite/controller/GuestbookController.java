package com.poscodx.mysite.controller;

import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping("")
	public String main(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		
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
