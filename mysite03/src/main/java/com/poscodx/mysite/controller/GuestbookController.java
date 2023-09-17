package com.poscodx.mysite.controller;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;

	@RequestMapping("")
	public String main(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/main";
	}

//	@RequestMapping("/add") // add action
//	public String add(GuestbookVo vo) {
//		guestbookRepository.insert(vo);
//		return "redirect:/";
//	}
//
//	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET) // delete form
//	public String delete(@PathVariable("no") Long no, Model model) {
//		model.addAttribute("no", no);
//		return "delete";
//	}
//
//	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST) // delete action
//	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
//		guestbookRepository.deleteByNoAndPassword(no, password);
//		return "redirect:/";
//	}
}
