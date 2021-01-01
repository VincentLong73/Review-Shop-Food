package com.soict.reviewshopfood.controller;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.ImageAvatarService;
import com.soict.reviewshopfood.service.impl.UserService;

@RestController
@RequestMapping(value = "/api/user")

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired

	private ImageAvatarService iImageAvatarService;
	
	//Lay thong tin user
	@GetMapping(value = "/getUserByEmail")
	public ResponseEntity<Object> getUserByEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();
		
		if(auth.getName() != null) {
			try {
				userModel = userService.findByEmailAfterLogin(auth.getName());
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.println(e);
			}
		}
		return new ResponseEntity<Object>(userModel, httpStatus);

	}

	// edit user
	@PutMapping(value = "/editUser", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> editUser(UserModel userModel) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = null;
		try {
			if (userService.editUser(userModel, auth.getName())) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}

	// upload anh avatar
	@PostMapping("/uploadImageAvatar")
	public ResponseEntity<String> uploadImageAvatar(@RequestParam("file") MultipartFile file) throws SQLException {

		HttpStatus httpStatus = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			iImageAvatarService.storeFileImageAvatar(file, auth.getName());
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		String imageUrl = iImageAvatarService.getImageAvatar(auth.getName());
		
		return new ResponseEntity<String>(imageUrl,httpStatus);
	}

	// lay anh tra ve fileName
	@GetMapping("/getImageAvatar")
	public ResponseEntity<Object> getImageAvatar1( HttpServletRequest request) throws SQLException {
		String imageUrl = null;
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			imageUrl = iImageAvatarService.getImageAvatar(auth.getName());
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new  ResponseEntity<Object>(imageUrl,httpStatus);
	}

//	@Autowired
//	private UserService userService;
	// Cap nhat lai password
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
	
	// Get user
		@GetMapping(value = "/getUser/{id}")
		public ResponseEntity<Object> getUser(@PathVariable("id") int id) {
			HttpStatus httpStatus = null;
			UserModel userModel = new UserModel();
			try {
				userModel = userService.getUserById(id);
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.println(e);
			}
			return new ResponseEntity<Object>(userModel, httpStatus);
		}
}
