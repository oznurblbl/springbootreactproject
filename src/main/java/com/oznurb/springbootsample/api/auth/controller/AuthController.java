package com.oznurb.springbootsample.api.auth.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oznurb.springbootsample.api.auth.payload.request.LoginRequest;
import com.oznurb.springbootsample.api.auth.payload.request.SignupRequest;
import com.oznurb.springbootsample.api.auth.payload.response.JwtResponse;
import com.oznurb.springbootsample.api.auth.payload.response.MessageResponse;
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
public class AuthController {

	/*Authentication ile ilgili url'leri yazmak için 
	 * AuthRestController adında bir RestController oluşturulur
	 * ve token almak için iki rest endpoint oluştururuz.*/
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/signin")
	@ResponseBody
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenUtil.generateJwtToken(authentication);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetailsImpl.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId(), userDetailsImpl.getUsername(), userDetailsImpl.getEmail(), roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest ){
		
		if (userService.existsByuserName(signupRequest.getUserName())) {
			return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userService.existsByEmail(signupRequest.getEmail())) {
			

			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use !"));
		}
		
		//Create new user's account
		User user = new User(signupRequest.getUserName(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));
		
		Set<String> strRoles = signupRequest.getRole();
		
		Set<Role> roles = new HashSet<Role>();
		
		if (strRoles == null) {
			
			
			Role userRole = roleService.findByName(ERole.ROLE_USER);
			
			if (userRole == null) {
				new RuntimeException("Error: Role is not found.");
			}
			
			roles.add(userRole);
		}else {
			strRoles.forEach(role -> {
					
					Role r = roleService.findByName(ERole.valueOf(role));
				
					if (r != null) {
						roles.add(r);
					}else {
						new RuntimeException("Error: Role is not found.");
					}
					
					
			});
		}
		
		user.setRoles(roles);
		userService.insertUser(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully !"));
	
	}
}
