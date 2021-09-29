package com.oznurb.springbootsample.api.auth.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {//Token almak için yapacağımız istekte kullanacağımız nesneyi hazırlarız.

	@NotBlank
	@Size(min = 3 , max = 20)
	private String userName;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	private Set<String> role ;
	
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
}
