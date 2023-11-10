package com.poscodx.mysite.dto;

public class JsonResult {
	private String result; 	// "success" or "fail"
	private Object data; 	// if success, set!
	private Long sno;
	private String message;	// if fail, set!
	
	private JsonResult(Object data, Long sno) {
		this.result = "success";
		this.data = data;
		this.sno = sno;
		
		System.out.println(sno);
	}
	
	private JsonResult(Object data) {
		this.result = "success";
		this.data = data;
	}
	
	public JsonResult(String message) {
		this.result = "fail";
		this.message = message;
	}
	
	public String getResult() {
		return result;
	}
	public Object getData() {
		return data;
	}
	public Object getSno() {
		return sno;
	}
	public String getMessage() {
		return message;
	}
	
	public static JsonResult success_sno(Object data, Long sno) {
		return new JsonResult(data, sno);
	}
	
	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}
	
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}
	
}
