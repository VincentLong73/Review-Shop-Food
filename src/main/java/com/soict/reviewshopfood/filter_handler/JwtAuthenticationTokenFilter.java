package com.soict.reviewshopfood.filter_handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.soict.reviewshopfood.helper.Utils;
import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.service.impl.UserService;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{
	
	private final static String TOKEN_HEADER = "Authorization";
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private Utils utils;

	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpRespone, FilterChain filterChain)
			throws ServletException, IOException {

		if(utils.checkCookies(httpRequest.getCookies())) {
			String authToken = utils.getCookie(httpRequest, TOKEN_HEADER).getValue();
			if(authToken != null && jwtService.validateToken(authToken)) {	
				String email = jwtService.getEmailToken(authToken);		
				UserDetails user;
				user = userService.loadUserByUsername(email);
				if(user!=null) {
					
					boolean enable = true;
					boolean accountNonExpired = true;
					boolean credentialsNonExpired = true;
					UserDetails userDetail = new User(email, user.getPassword(),enable,accountNonExpired,credentialsNonExpired,accountNonExpired, user.getAuthorities());
					
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,userDetail.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);	
				}		
			}
		}	
		filterChain.doFilter(httpRequest, httpRespone);	
	}
}

