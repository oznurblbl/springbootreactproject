package com.oznurb.springbootsample.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.oznurb.springbootsample.entity.ERole;
import com.oznurb.springbootsample.entity.Role;
import com.oznurb.springbootsample.repository.RoleRepository;
import com.oznurb.springbootsample.service.RoleService;


@Component
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role findByName(ERole name) {
		
		return roleRepository.findByName(name);
	}

}
