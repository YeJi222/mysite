package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poscodx.mysite.vo.GuestbookVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public Boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		boolean result = count == 1;

		return result;
	}

	public Boolean deleteByNoAndPassword(long no, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		
		int count = sqlSession.delete("guestbook.deleteByNoAndPassword", map);
		return count == 1;
	}
}
