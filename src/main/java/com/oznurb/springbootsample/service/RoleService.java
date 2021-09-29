package com.oznurb.springbootsample.service;


import org.springframework.stereotype.Service;

import com.oznurb.springbootsample.entity.ERole;
import com.oznurb.springbootsample.entity.Role;

@Service
public interface RoleService {

	public Role findByName(ERole name);
}
