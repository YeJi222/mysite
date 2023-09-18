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
	
	// @Autowired
	//private MailSender mailSender;
	
	public void join(UserVo vo) {
		userRepository.insert(vo);
		// mailSender.send(vo.getEmail(), "", "");
	}

	public UserVo getUser(String email, String password) {
		UserVo authUser = userRepository.findByEmailAndPassword(email, password);
		
		return authUser;
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
