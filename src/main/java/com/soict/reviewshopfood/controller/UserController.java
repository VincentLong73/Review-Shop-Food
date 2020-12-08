package com.soict.reviewshopfood.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.UserService;



@RestController
@RequestMapping(value="/review-shop-food")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> login(User user) {	
	String result = "";
		HttpStatus httpStatus = null;
		try {
			System.out.println(2);
			if (userService.checkLogin(user)) {
				result = jwtService.generateTokenLogin(user.getUserName());
				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong userId and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		HttpStatus httpStatus = null;
		String result = "error";
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			httpStatus = HttpStatus.OK;
			result = "ok";
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

	
	@PostMapping(value="/addUser",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addUSer(UserModel userModel){
		
		userService.addUser(userModel);
		return new ResponseEntity<Object>("Add a new user successfully",HttpStatus.OK);
	}
	
	@PutMapping(value = "/applyNewPassword",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> applyNewPassword( User user) {

		userService.applyNewPassword(user);

		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}
}
