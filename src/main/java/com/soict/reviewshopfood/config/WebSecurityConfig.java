package com.soict.reviewshopfood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soict.reviewshopfood.filter_handler.CustomAccessDeniedHandler;
import com.soict.reviewshopfood.filter_handler.JwtAuthenticationTokenFilter;
import com.soict.reviewshopfood.filter_handler.RestAuthenticationEntryPoint;
import com.soict.reviewshopfood.service.impl.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {

		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		//jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;

	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();

	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {

		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
//		http.csrf().disable();
//		http.authorizeRequests().antMatchers("/aims/login","/aims/logout","/aims/editProduct/*","/aims/deleteProduct/*","/users/*").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/api/auth/**").permitAll();
		httpSecurity.csrf().ignoringAntMatchers("/api/**");
		httpSecurity.antMatcher("/api/**")
				.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				
				.antMatchers("/api/user/**").hasAnyAuthority("ROLE_CUSTOMER","ROLE_SHOP","ROLE_ADMIN")
				
				.antMatchers(HttpMethod.GET,"/api/image/**").hasAnyAuthority("ROLE_CUSTOMER","ROLE_SHOP","ROLE_ADMIN")
				.antMatchers(HttpMethod.POST,"/api/image/uploadImageAvatar/**").hasAnyAuthority("ROLE_CUSTOMER","ROLE_SHOP","ROLE_ADMIN")
				.antMatchers(HttpMethod.POST,"/api/image/uploadImageFood/**").access("hasRole('ROLE_SHOP')")
				
				.antMatchers(HttpMethod.GET,"/api/food/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/food/**").access("hasRole('ROLE_SHOP')")
				.antMatchers(HttpMethod.PUT,"/api/food/**").access("hasRole('ROLE_SHOP')")
				.antMatchers(HttpMethod.DELETE,"/api/food/**").access("hasRole('ROLE_SHOP')")
				
				.antMatchers(HttpMethod.GET,"/api/comment/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/comment/**").access("hasRole('ROLE_CUSTOMER')")
				.antMatchers(HttpMethod.PUT,"/api/comment/**").access("hasRole('ROLE_CUSTOMER')")
				
				.antMatchers(HttpMethod.GET,"/api/shop/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/shop/**").access("hasRole('ROLE_SHOP')")
				.antMatchers(HttpMethod.PUT,"/api/shop/**").access("hasRole('ROLE_SHOP')")
				.antMatchers(HttpMethod.DELETE,"/api/shop/**").access("hasRole('ROLE_ADMIN')")
				
				
				.antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers(HttpMethod.GET,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers(HttpMethod.POST,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers(HttpMethod.PUT,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers(HttpMethod.DELETE,"/api/admin/**").access("hasRole('ROLE_ADMIN')")
				
				
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
