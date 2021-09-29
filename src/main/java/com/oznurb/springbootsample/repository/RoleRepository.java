package com.oznurb.springbootsample.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oznurb.springbootsample.entity.ERole;
import com.oznurb.springbootsample.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByName(ERole name);
}
