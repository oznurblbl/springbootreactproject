package com.oznurb.springbootsample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oznurb.springbootsample.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByuserName(String username);
	
	public User findByEmail(String email);
	
	public Boolean existsByuserName(String userName);
	
	public Boolean existsByEmail(String email);

}
