package com.signinsignup.plug.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.signinsignup.plug.sso.dao.UsersRepository;
import com.signinsignup.plug.sso.model.Users;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository userRepo;
	
	public Users saveUser(Users user) {
		return userRepo.save(user);
	}
	
	public Users findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

}
