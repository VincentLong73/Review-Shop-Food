package com.soict.reviewshopfood.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.helper.Utils;
import com.soict.reviewshopfood.jwt.JwtService;
import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.FoodService;
import com.soict.reviewshopfood.service.impl.UserService;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private Utils utils;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private FoodService foodService;

	@RequestMapping(value = "/home")
	public ResponseEntity<Object> home(HttpServletResponse response, HttpServletRequest request) {

		return new ResponseEntity<Object>("Access successfully!", HttpStatus.OK);
	}

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

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
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
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}

	// lay cac mon an con actice theo so luong view
	@RequestMapping(value = "/getFoodByOrderByViewAscAndIsDelete")
	public ResponseEntity<Object> getFoodByOrderByViewAscAndActive() {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getFoodByView();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
	}

}
