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
		System.out.println(vo);
		userRepository.insert(vo);
		System.out.println(vo);
		// mailSender.send(vo.getEmail(), "", "");
	}

	public UserVo getUser(String email, String password) {
		UserVo authUser = userRepository.findByEmailAndPassword(email, password);
		
		return authUser;
	}

	public UserVo getUser(Long no) {
		UserVo authUser = userRepository.findByNo(no);
		
		return authUser;
	}

	public void update(UserVo userVo) {
		userRepository.update(userVo);
	}
}
