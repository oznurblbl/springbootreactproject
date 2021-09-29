package com.oznurb.springbootsample.api.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oznurb.springbootsample.api.auth.payload.request.LoginRequest;
import com.oznurb.springbootsample.api.auth.payload.request.SignupRequest;
import com.oznurb.springbootsample.api.auth.payload.response.JwtResponse;
import com.oznurb.springbootsample.api.auth.payload.response.MessageResponse;
import com.oznurb.springbootsample.api.user.dto.UserDto;
import com.oznurb.springbootsample.api.user.mapper.UserMapper;
import com.oznurb.springbootsample.entity.ERole;
import com.oznurb.springbootsample.entity.Role;
import com.oznurb.springbootsample.entity.User;
import com.oznurb.springbootsample.security.jwt.JwtTokenUtil;
import com.oznurb.springbootsample.security.services.serviceImpl.UserDetailsImpl;
import com.oznurb.springbootsample.service.RoleService;
import com.oznurb.springbootsample.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {
 	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	
	@RequestMapping("/")
	public String index() {
		
		return "Hi! there, User Application";
	}
	
	
	@GetMapping
	public List<UserDto> getUsers() {
		return userMapper.entityListToDtoList(userService.getUsers());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserDto getUserById(@PathVariable("id") long id) {
		
		return userMapper.entityToDto(userService.getUserById(id));
	}
	
	@PostMapping                                                        
	public UserDto insertUser(@RequestBody UserDto userDto) {
		try {
			User user = userMapper.dtoToEntity(userDto);
			if(user != null) {
				User reUser = userService.insertUser(user);
		
				return userMapper.entityToDto(reUser);
		}
			}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PutMapping
	public UserDto updateUser(@RequestBody UserDto userDto) {
		
		try {
			User user = userMapper.dtoToEntity(userDto);
			if (user != null) {
				User reUser = userService.updateUser(user);
				
				return userMapper.entityToDto(reUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseBody
	public void deleteUser( @PathVariable("id") Long id) {
		userService.deleteUser(id);
	}
}
