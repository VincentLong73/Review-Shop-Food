package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.helper.Utils;
import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.model.UserEditFormModel;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private Utils utils;
	@Autowired
	private JwtService jwtService;

	@GetMapping(value = "/getUser")
	public ResponseEntity<Object> getUser(HttpServletRequest req) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		UserModel userModel = new UserModel();
		try {
			Cookie jwt = utils.getCookie(req, "Authorization");
			if (null != jwt) {
				userModel = userService.getUserByEmail(jwtService.getEmailToken(jwt.getValue()));
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<Object>(userModel, httpStatus);
	}

	@PostMapping(value = "/edit", produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> editUser(UserEditFormModel userEditFormModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		if (auth.isAuthenticated()) {
			User user = userService.findByEmail(auth.getName());
			user.setUserName(userEditFormModel.getUserName());
			user.setFullName(userEditFormModel.getFullName());
			if (userEditFormModel.getPassword() != null) {
				if (user.getPassword().equals(userEditFormModel.getPassword())) {
					user.setPassword(userEditFormModel.getNewPassword());
				}
			}
			userService.updateUser(user);
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<String>(httpStatus);
	}
	
	@PutMapping(value="/editUser",produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> editUser(UserModel userModel){
		HttpStatus httpStatus = null;
		try {
			if(userService.editUser(userModel)) {
				httpStatus = HttpStatus.OK;
			}else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}
	
	
//	@Autowired
//	private UserService userService;
	//Cap nhat lai password
//	@PutMapping(value = "/applyNewPassword",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public ResponseEntity<Object> applyNewPassword( User user) {
//		HttpStatus httpStatus = null;
//		try {
//			userService.applyNewPassword(user);
//			httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			System.out.println(e);
//		}
//		return new ResponseEntity<Object>(httpStatus);
//	}
}
