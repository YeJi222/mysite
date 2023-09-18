package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void addUser(UserVo vo) {
		userRepository.insert(vo);
	}

	public boolean loginAction(String email, String password) {

		return false;
	}

	

//	public List<UserVo> getContentsList() {
//		return userRepository.findAll();
//	}
//	
//	public Boolean deleteContents(Long no, String password) {
//		return userRepository.deleteByNoAndPassword(no, password);
//	}
//	
//	public Boolean addContents(GuestbookVo vo) { 
//		return userRepository.insert(vo);
//	}
}
