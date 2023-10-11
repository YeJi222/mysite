package com.poscodx.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.poscodx.mysite.vo.SiteVo;

@SpringBootApplication
public class MySiteApplication {

	public static void main(String[] args) {
		
		ApplicationContext ac = SpringApplication.run(MySiteApplication.class, args);
		
		SiteVo site = ac.getBean(SiteVo.class);
		System.out.println(site);
		
	}

}
