package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.helper.Utils;
import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping(value="/api/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private Utils utils;
	@Autowired
	private JwtService jwtService;

	@PostMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<Object> login(User user, HttpServletResponse response, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		String status = null;
		try {
			if (userService.checkLogin(user)) {
				String result = jwtService.generateTokenLogin(user.getEmail());
				Cookie jwt = utils.createCookie("Authorization", result, true, (long) 3600);

				response.addCookie(jwt);
				httpStatus = HttpStatus.OK;
				status = "Login successfully";
			}else {
				status = "Email or Password is wrong!";
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return new ResponseEntity<Object>(status, httpStatus);
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		if (auth != null) {
			Cookie jwt = utils.createCookie("Authorization", null, true, (long) 0);
			response.addCookie(jwt);
			new SecurityContextLogoutHandler().logout(request, response, auth);
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<String>(httpStatus);
	}

	// dang ki tai khoan nguoi dung
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addUSer(UserModel userModel) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			userModel.setCodeRole("ROLE_CUSTOMER");
			if (userService.addUser(userModel)) {
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<Object>(httpStatus);
	}
}
