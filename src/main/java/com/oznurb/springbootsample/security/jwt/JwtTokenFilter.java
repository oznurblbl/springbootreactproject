package com.oznurb.springbootsample.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oznurb.springbootsample.security.services.serviceImpl.UserDetailsServiceImpl;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	/*JwtFilter classı, OncePerRequestFilter classından 
	 * kalıtım alır. Bu sayede her istek gönderildiğinde
	 *  doFilterInternal metodu çalışacaktır.*/
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = parseJwt(request);
			if(jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
				String userName = jwtTokenUtil.getUserNameFromJwtToken(jwt);
				
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {} ", e);
		}
		
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth. substring(7, headerAuth.length());
		}
		
		return null;
	}


	
	
}
