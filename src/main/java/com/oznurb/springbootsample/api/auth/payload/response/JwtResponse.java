package com.oznurb.springbootsample.api.auth.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private long id;
	private String userName;
	private String email;
	private List<String > roles;
	
	public JwtResponse(String token, long id, String userName, String email, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.roles = roles;
	}
	
	
}
