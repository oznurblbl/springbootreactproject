package com.oznurb.springbootsample.api.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String userName;
	private String email;
	private String password;
	
	
	
	
}
