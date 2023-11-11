package com.poscodx.mysite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.service.SiteService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/main")
public class MainController {
	private final SiteService siteService;

	public MainController(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@GetMapping("")
	public ResponseEntity<JsonResult> read() {	
		log.info("Request[GET /api/main]");
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JsonResult.success(siteService.getSite()));
	}

	/*
	@PostMapping("")
	public ResponseEntity<JsonResult> add(@RequestBody GuestbookVo vo) {
		log.info("Request[POST /api/guestbook]:"+ vo);
		
		guestbookService.addContents(vo);
		vo.setPassword("");	
		
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(vo));
	}

	@DeleteMapping("/{no}")
	public ResponseEntity<JsonResult> delete(
			@PathVariable("no") Long no, 
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		
		Boolean result = guestbookService.deleteContents(no, password);		
		return ResponseEntity.status(HttpStatus.OK).body(JsonResult.success(result ? no : null));
	}
	*/
}