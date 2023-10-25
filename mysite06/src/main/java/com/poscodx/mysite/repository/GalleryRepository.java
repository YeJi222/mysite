package com.poscodx.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(GalleryVo galleryVo) {
		return sqlSession.insert("gallery.insert", galleryVo);
	}

	public List<GalleryVo> findImages() {
		return sqlSession.selectList("gallery.findImages");
	}

	public int delete(Long no) {
		return sqlSession.delete("gallery.deleteByNo", no);
	}
}
