package com.soict.reviewshopfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.UserService;



@RestController
@RequestMapping(value="/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/getUser/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") int id){
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();
		try {
			userModel = userService.getUserById(id);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(userModel,httpStatus);
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
