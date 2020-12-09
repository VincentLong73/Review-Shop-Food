package com.soict.reviewshopfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.RatingOfFood;
import com.soict.reviewshopfood.service.impl.RateService;
@RestController
@RequestMapping(value="/review-shop-food")
public class RatingController {
	@Autowired
	private RateService rateService;
	@RequestMapping(value="/getRating/{foodId}")
	public ResponseEntity<RatingOfFood> getRating(@PathVariable("foodId") int foodId){
		return new ResponseEntity<RatingOfFood>(rateService.getListRateByFoodId(foodId),HttpStatus.OK);
	}
}
