package com.poscodx.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public JsonResult read(@RequestParam(name = "sno") Long sno) {
		System.out.println("sno : " + sno);
		
		List<GuestbookVo> list = guestbookService.geGuestbookList(sno);
		System.out.println(list);
		
		return JsonResult.success_sno(list, sno);
	}
	
//	@GetMapping
//	public JsonResult getRowNo(@RequestParam(name = "startNo") Long startNo) {
//		System.out.println("startNo" + startNo);
//		
//		Long row = guestbookService.getRowNo(startNo);
//		
//		return JsonResult.success(row);
//	}
	
	@DeleteMapping("{no}")
	public JsonResult delete(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		
		Boolean result = guestbookService.deleteContents(no, password);
		
		if(result == true) return JsonResult.success("삭제 성공");
		else return JsonResult.fail("비밀번호가 올바르지 않습니다.");
	}
}
