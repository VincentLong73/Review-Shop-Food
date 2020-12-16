package com.soict.reviewshopfood.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.UserService;

@RestController
@RequestMapping(value="/api/home1")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@RequestMapping(value = "/home")
	public ResponseEntity<Object> home(HttpServletResponse response, HttpServletRequest request) {

		return new ResponseEntity<Object>("Access successfully!", HttpStatus.OK);
	}
	
	//@PostMapping(value = "/login", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(@RequestBody User user,HttpServletResponse response, HttpServletRequest request) {	
		String result = "";
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();
		Cookie[] cookies = request.getCookies();
		try {
			if (userService.checkLogin(user, cookies)) {
				result = jwtService.generateTokenLogin(user.getEmail());
				httpStatus = HttpStatus.OK;
				Cookie jwt = new Cookie("Authorization", result);
				jwt.setHttpOnly(true);
				jwt.setPath("/");
				response.addCookie(jwt);
				userModel = userService.findByEmailAfterLogin(user.getEmail());
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Object>(userModel,httpStatus);
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

	//dang ki tai khoan nguoi dung
	@PostMapping(value="/register",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addUSer(UserModel userModel){
		HttpStatus httpStatus = null;
		try {
			userModel.setCodeRole("ROLE_CUSTOMER");
			userService.addUser(userModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	@GetMapping(value="/getUser/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") int id){
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();
		try {
			userModel.setCodeRole("ROLE_CUSTOMER");
			userService.addUser(userModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(userModel,httpStatus);
	}
	
}
