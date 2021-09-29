 package com.oznurb.springbootsample.security.services.serviceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oznurb.springbootsample.entity.User;

import lombok.Getter;


@Getter

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private long id;
	
	private String userName;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UserDetailsImpl(long id, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		
		List<GrantedAuthority>  authorities = user.getRoles().stream()
				//map: Stream içindeki yığınsal olarak bulunan her veriye özgü işlemler yapmayı sağlar.
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				//collect: stream nesnesinden List nesnesi üretilir
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(
				user.getId(),
				user.getUserName(),
				user.getEmail(),
				user.getPassword(),authorities
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) 
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		
		UserDetailsImpl user = (UserDetailsImpl) obj;
		return Objects.equals(id, user.id);
		}
	}

