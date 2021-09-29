package com.oznurb.springbootsample.security.services.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oznurb.springbootsample.api.user.mapper.UserMapper;
import com.oznurb.springbootsample.entity.User;
import com.oznurb.springbootsample.repository.UserRepository;
import com.oznurb.springbootsample.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userService.findByuserName(username);
		
//		if (user == null) {
//			
//			user = userRepository.findByEmail(email);
					
					
		if (user == null) {
			
			return (UserDetails) new UsernameNotFoundException("User not Found with username :" + username);
		}
			
//		}
		
		return UserDetailsImpl.build(user);
		
		
	}
	
	
}
