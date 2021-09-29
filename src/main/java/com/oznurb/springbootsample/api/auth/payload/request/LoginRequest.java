package com.oznurb.springbootsample.api.auth.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {//Token almak için yapacağımız istekte kullanacağımız nesneyi hazırlarız.
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;
	

}
