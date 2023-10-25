package com.poscodx.mysite.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;


@RestController("guestbookApiController") 
@RequestMapping("/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@PostMapping
	public JsonResult add(@RequestBody GuestbookVo vo) {
		guestbookService.addContents(vo);
		
		return JsonResult.success(vo);
	}

	@GetMapping
	public JsonResult read() {
		List<GuestbookVo> list = guestbookService.getContentsList();

		return JsonResult.success(list);
	}
	
	@DeleteMapping("{no}")
	public JsonResult delete(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		
		// System.out.println("delete - " + no);
		Boolean result = guestbookService.deleteContents(no, password);
		// System.out.println(result);
		
		if(result == true) return JsonResult.success("삭제 성공");
		else return JsonResult.fail("비밀번호가 올바르지 않습니다.");
	}
}
