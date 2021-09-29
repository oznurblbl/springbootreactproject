package com.oznurb.springbootsample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oznurb.springbootsample.security.jwt.JwtAuthenticationEntryPoint;
import com.oznurb.springbootsample.security.jwt.JwtTokenFilter;
import com.oznurb.springbootsample.security.services.serviceImpl.UserDetailsImpl;
import com.oznurb.springbootsample.security.services.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(//securedEnabled = true, jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
	public JwtTokenFilter authenticationJwtTokenFilter() {
		return new JwtTokenFilter();
	}
	
	@Autowired
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/*Her istek gönderdiğimizde Spring Security,
	 * bizden authentication bilgisi isteyecektir.
	 *  Fakat bizim login olabilmek için /signin, /sinup url'lerini authentication
	 *   kontrolü dışına çıkarmalıyız. 
	 *  Bu işlemi de SpringSecurityConfig 
	 *  dosyasına aşağıdaki metodu override ederek sağlarız.*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/api/test/**").permitAll()
		.anyRequest().authenticated();
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
