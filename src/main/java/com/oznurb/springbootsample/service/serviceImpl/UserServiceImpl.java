package com.oznurb.springbootsample.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oznurb.springbootsample.entity.User;
import com.oznurb.springbootsample.repository.UserRepository;
import com.oznurb.springbootsample.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> getUsers() {

		return repository.findAll();
	}

	@Override
	public User getUserById(long id) {

		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			User reUser = user.get();
			return reUser;
		}

		return null;
	}

	@Override
	public void deleteUser(long id) {

		repository.deleteById(id);

	}

	@Override
	public User updateUser(User user) {

		return repository.saveAndFlush(user);
	}

	@Override
	public User insertUser(User user) {

		return repository.save(user);
	}

	@Override
	public User findByuserName(String username) {
		// TODO Auto-generated method stub
		return repository.findByuserName(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.existsByEmail(email);
	}
	
	@Override
	public Boolean existsByuserName(String username) {
		// TODO Auto-generated method stub
		return repository.existsByuserName(username);
	}

}