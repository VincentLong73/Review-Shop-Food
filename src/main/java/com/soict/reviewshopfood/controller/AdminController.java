package com.soict.reviewshopfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.model.ShopModel;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.FoodService;
import com.soict.reviewshopfood.service.impl.ShopService;
import com.soict.reviewshopfood.service.impl.UserService;

@RestController
@RequestMapping(value="/review-shop-food/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private FoodService foodService;
	
	@GetMapping(value="/listCustomer/{code}")
	public ResponseEntity<List<UserModel>> getListCustomer(@PathVariable("code") String code){
		return new ResponseEntity<List<UserModel>>(userService.getListUserByRoleId(code),HttpStatus.OK);
	}
	@GetMapping(value="/listShop")
	public ResponseEntity<List<ShopModel>> getListShop(){
		return new ResponseEntity<List<ShopModel>>(shopService.getListShop(),HttpStatus.OK);
	}
	@GetMapping(value="/listFood")
	public ResponseEntity<List<FoodModel>> getListFood(){
		return new ResponseEntity<List<FoodModel>>(foodService.getListFood(),HttpStatus.OK);
	}

}
