


package com.oznurb.springbootsample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oznurb.springbootsample.entity.User;

@Service
public interface UserService {
	
	public List<User> getUsers();
	
	public User getUserById(long id);
	
	public User insertUser(User user);
	
	public User updateUser(User user);
	
	public void deleteUser(long id);
	
	public User findByuserName(String username);
	
	public Boolean existsByuserName(String username);
	
	public Boolean existsByEmail(String email);

	
}
