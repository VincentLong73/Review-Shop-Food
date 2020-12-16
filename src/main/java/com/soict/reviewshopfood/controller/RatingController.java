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
@RequestMapping(value="/api/rating")
public class RatingController {
	@Autowired
	private RateService rateService;
	@RequestMapping(value="/getRating/{foodId}")
	public ResponseEntity<Object> getRating(@PathVariable("foodId") int foodId){
		HttpStatus httpStatus = null;
		RatingOfFood ratingOfFood = null;
		try {
			ratingOfFood = rateService.getListRateByFoodId(foodId);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<Object>(ratingOfFood,httpStatus);
	}
}
