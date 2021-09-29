package com.oznurb.springbootsample.api.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//cors(cross origin request sharing) : domainler arası kaynak alışverişi 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/all")
	public String allAccess() {
		
		return "public Content";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		
		return "User Content";
	}
	
	@GetMapping("/mod")
	/*PreAuthorize : Yetkilendirme için kullanılır.
	 * Metoda girmeden önce çalışır*/ 
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		
		return "Moderator Board";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		
		return "Admin Board";
	}
}
