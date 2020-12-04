package com.soict.reviewshopfood.filter_handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.service.IUserService;



public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter{
	
	private final static String TOKEN_HEADER = "authorization";

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		
		if(jwtService.validateToken(authToken)) {
			
			String username = jwtService.getUsernameToken(authToken);
			
			com.soict.reviewshopfood.entity.User user = userService.findByUserName(username);
			
			if(user!=null) {
				
				boolean enable = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				UserDetails userDetail = new User(username, user.getPassword(),enable,accountNonExpired,credentialsNonExpired,accountNonExpired, user.getAuthorities());
			
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			
			}
			
		}
		
		chain.doFilter(request, response);
	}
}
