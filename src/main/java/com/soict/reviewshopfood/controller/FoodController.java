package com.soict.reviewshopfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.service.impl.FoodService;

@RestController
@RequestMapping(value="/review-shop-food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@RequestMapping(value="/getFood",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<FoodModel> getFood(int id){
		return new ResponseEntity<FoodModel>(foodService.getFoodByShopId(id), HttpStatus.OK);	
	}
	
	@RequestMapping(value="/getFoodByNameFood",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<List<FoodModel>> getFoodByNameFood(@PathVariable("nameShop") String nameShop){
		return new ResponseEntity<List<FoodModel>>(foodService.getListFoodByNameFood(nameShop), HttpStatus.OK);
	}
	
	@RequestMapping(value="/addFood", method = RequestMethod.POST ,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addFood(FoodModel foodModel){
		foodService.addFood(foodModel);
		return new ResponseEntity<Object>("Add food successfully!", HttpStatus.OK);
	}
	
	@PutMapping(value="/editFood" ,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> editFood(FoodModel foodModel){
		foodService.editFood(foodModel);
		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}

}
