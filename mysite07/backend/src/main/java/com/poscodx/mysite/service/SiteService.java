package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.repository.SiteRepository;
import com.poscodx.mysite.vo.SiteVo;

@Service
public class SiteService {
	final SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		return siteRepository.find();
	}
	
	public void updateSite(SiteVo vo) {
		siteRepository.update(vo);
	}
}