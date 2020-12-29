package com.soict.reviewshopfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.model.ShopModel;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.FoodService;
import com.soict.reviewshopfood.service.impl.ShopService;
import com.soict.reviewshopfood.service.impl.UserService;

@RestController
@RequestMapping(value="/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private FoodService foodService;
	
	//Lay list user theo role (customer, bos_shop, admin)
	@GetMapping(value="/listCustomer/{codeRole}")
	public ResponseEntity<Object> getListCustomer(@PathVariable("codeRole") String codeRole){
		HttpStatus httpStatus = null;
		List<UserModel> userModels = new ArrayList<UserModel>();
		try {
			userModels = userService.getListUserByRoleId(codeRole);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(userModels,httpStatus);
	}
	//Lay list shop
	@GetMapping(value="/listShop")
	public ResponseEntity<Object> getListShop(){
		HttpStatus httpStatus = null;
		List<ShopModel> shopModels = new ArrayList<ShopModel>();
		try {
			shopModels = shopService.getListShop();
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(shopModels,httpStatus);
	}
	//Lay list food
	@GetMapping(value="/listFood")
	public ResponseEntity<Object> getListFood(){
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getListFood();
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels,httpStatus);
	}
	//dang ki tai khoan chu shop
	@PostMapping(value="/register",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> addUSer(UserModel userModel){
		HttpStatus httpStatus = null;
		try {
			userModel.setCodeRole("ROLE_SHOP");
			userService.addUser(userModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
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
			userService.blockOrUnblockUser(idUser,false);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}
	//unBlock User theo idUser
	@PutMapping(value = "/unBlockUser/{idUser}")
	public ResponseEntity<String> unBlockUser(@PathVariable("idUser") int idUser) {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		try {
			userService.blockOrUnblockUser(idUser,true);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}
}
