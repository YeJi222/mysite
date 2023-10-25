package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GalleryRepository;
import com.poscodx.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	
	public List<GalleryVo> getImages() {
		return null;
	}
	
	public void removeImage(Long no) {
	}
	
	public void addImage(GalleryVo galleryVo) {
		galleryRepository.insert(galleryVo);
	}
}
