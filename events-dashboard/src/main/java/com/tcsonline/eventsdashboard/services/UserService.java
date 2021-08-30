package com.tcsonline.eventsdashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcsonline.eventsdashboard.entities.User;
import com.tcsonline.eventsdashboard.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	public List<User> findAllUsers() {
		return repo.findAll();
	}
	

	public List<User> findAllUsersByUsername(String username) {
		return repo.findByUsername(username);
	}
}
