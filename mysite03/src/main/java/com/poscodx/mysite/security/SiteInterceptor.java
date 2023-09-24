package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.poscodx.mysite.controller.AdminController;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("site interceptor preHandle...");
		
		SiteVo siteVo = siteService.getSite();
		
		ConfigurableApplicationContext appContext = (ConfigurableApplicationContext) applicationContext;
		
		if (!applicationContext.containsBean("siteVo")) { // 빈 등록 한 번만 실행 
			appContext.getBeanFactory().registerSingleton("siteVo", siteVo);
		}
		
		// 빈이 정상적으로 등록되었는지 확인
        SiteVo registeredSiteVo = applicationContext.getBean("siteVo", SiteVo.class);
        System.out.println("Site Interceptor registeredSiteVo : " + registeredSiteVo);
		
		// 가져온 SiteVo를 request 속성에 저장합니다.
        request.setAttribute("siteVo", registeredSiteVo);

        return true;
	}
}