package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.model.ShopModel;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.FoodService;
import com.soict.reviewshopfood.service.impl.ShopService;
import com.soict.reviewshopfood.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private FoodService foodService;
	@Autowired
	private ModelMapper modelMapper;

	//Lay list user theo role (customer, bos_shop, admin)
	@GetMapping(value = "/listCustomer")
	public ResponseEntity<Object> getListCustomer() {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<UserModel> userModels = new ArrayList<UserModel>();
		try {
			userModels = userService.getListUser();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new ResponseEntity<Object>(userModels, httpStatus);
	}

	//Lay list shop
	@GetMapping(value = "/listShop")
	public ResponseEntity<Object> getListShop() {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;;
		List<UserModel> userModels = new ArrayList<UserModel>();
		try {
			List<User> users = userService.getShopActive();
			users.forEach(user -> {
				UserModel userModel = modelMapper.map(user, UserModel.class);
				userModel.setShopModel(modelMapper.map(user.getShop(), ShopModel.class));
				userModels.add(userModel);
			});
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new ResponseEntity<Object>(userModels, httpStatus);
	}

	//Lay list food
	@GetMapping(value = "/listFood")
	public ResponseEntity<Object> getListFood() {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getListFood();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
	}
	//dang ki tai khoan chu shop
//	@PostMapping(value="/register",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
//	public ResponseEntity<Object> addUSer(UserModel userModel){
//		HttpStatus httpStatus = null;
//		try {
//			userModel.setCodeRole("ROLE_SHOP");
//			userService.addUser(userModel);
//			httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			System.out.println(e);
//		}
//		return new ResponseEntity<Object>(httpStatus);
//	}

	@PutMapping(value = "/activeShop", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> activeShop(int shopId) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			if (shopService.activeShop(shopId)) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	//block User theo idUser
	@PutMapping(value = "/blockUser/{idUser}")
	public ResponseEntity<String> blockUser(@PathVariable("idUser") int idUser) {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		try {
			userService.blockOrUnblockUser(idUser, false);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}

	//unBlock User theo idUser
	@GetMapping(value = "/unBlockUser/{id}")
	public ResponseEntity<String> unBlockUser(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		try {
			userService.blockOrUnblockUser(id, true);
			httpStatus = HttpStatus.ACCEPTED;
		} catch (Exception e) {
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			e.getStackTrace();
		}
		return new ResponseEntity<String>(httpStatus);
	}

	@GetMapping(value = "/getRequestCreateShop", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> getRequestCreateShop() {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<UserModel> userModels = new ArrayList<UserModel>();
		if (auth.isAuthenticated()) {
			try {
				Date date = new Date();
				List<User> users = userService.getRequestCreateShop(date);
				users.forEach(user -> {
					UserModel userModel = modelMapper.map(user, UserModel.class);
					userModel.setShopModel(modelMapper.map(user.getShop(), ShopModel.class));
					userModels.add(userModel);
				});
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(userModels, httpStatus);
	}

	@GetMapping(value = "/deleteShop/{id}")
	public ResponseEntity<Object> deleteShop(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				userService.deleteUser(id);
				httpStatus = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(httpStatus);
	}

	@GetMapping(value = "/unDeleteShop/{id}")
	public ResponseEntity<Object> unDeleteShop(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				userService.unDeleteUser(id);
				httpStatus = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(httpStatus);
	}

	@GetMapping(value = "/unActive/{id}")
	public ResponseEntity<Object> unActive(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				userService.unDeleteUser(id);
				httpStatus = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	@GetMapping(value = "/toggleActive/{id}")
	public ResponseEntity<Object> toggleActiveUser(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				userService.toggleActive(id);
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	@GetMapping(value = "/getCountFood")
	public ResponseEntity<Object> getCountFood() {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		long total = 0;
		if (auth.isAuthenticated()) {
			try {
				total = foodService.getTotalFood();
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(total, httpStatus);
	}
	@GetMapping(value = "/getCountCustomer")
	public ResponseEntity<Object> getCountCustomer() {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		long total = 0;
		if (auth.isAuthenticated()) {
			try {
				total = userService.getTotalCustomer();
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(total, httpStatus);
	}
	@GetMapping(value = "/getCountShop")
	public ResponseEntity<Object> getCountShop() {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		long total = 0;
		if (auth.isAuthenticated()) {
			try {
				total = userService.getTotalShop();
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(total, httpStatus);
	}
	@GetMapping(value = "/deleteFood/{id}")
	public ResponseEntity<Object> deleteFood(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				foodService.deleteFood(id);
				httpStatus = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	@GetMapping(value = "/unDeleteFood/{id}")
	public ResponseEntity<Object> unDeleteFood(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				foodService.unDeleteFood(id);
				httpStatus = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(httpStatus);
	}
}
