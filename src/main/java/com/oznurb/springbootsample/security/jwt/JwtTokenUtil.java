package com.oznurb.springbootsample.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.oznurb.springbootsample.security.services.serviceImpl.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
	/*
	 * This class has 3 functions:
		generate a JWT from username, date, expiration, secret
		get username from JWT
		validate a JWT
		
		Remember that we’ve added bezkoder.app.jwtSecret and bezkoder.app.jwtExpirationMs properties in application.properties file.
	  */

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	//hash işlemi yaparken kullanılacak key
	@Value("${oznurb.app.jwtSecret}")
	private String jwtSecret;
	@Value("${oznurb.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	//UserDetails objesini alır ve bize token değerini gönderir.
	public String generateJwtToken(Authentication authentication) {
		
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		return Jwts.builder()
				//ilgili kullanıcı bilgisi atanır.
				.setSubject((userPrincipal.getUsername()))
				//tokenın başlangıç zamanı
				.setIssuedAt(new Date())
				//tokenın bitiş zamanı
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				//kullanılan algoritma ve bu algoritma çalışırken kullanılacak key değeri
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		
	}
	
	//
	public String getUserNameFromJwtToken(String token) {
		
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
				
	}
	
	// token hala geçerli mi? kullanıcı adı doğru ise ve token ın geçerlilik süresi devam ediyorsa true döner.
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {

			logger.error("Invalid JWT signature: {}", e.getMessage());
		}catch (MalformedJwtException e) {

			logger.error("Invalid JWT token: {}", e.getMessage());
		}catch (ExpiredJwtException e) {

			logger.error("JWT token is unsupported: {}", e.getMessage());
		}catch (UnsupportedJwtException e) {

			logger.error("Jwt token is unsupported: {}", e.getMessage());
		}catch (IllegalArgumentException e) {

			logger.error("JWT claims string is empty: {} ", e.getMessage());
		}
		
		return false;
	}
}
