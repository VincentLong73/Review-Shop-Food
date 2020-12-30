package com.soict.reviewshopfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.service.impl.FoodService;

@RestController
@RequestMapping(value = "/api/food")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FoodController {

	@Autowired
	private FoodService foodService;

	// lay mon an theo id va con ban
	@RequestMapping(value = "/getFood/{id}")
	public ResponseEntity<Object> getFood(@PathVariable("id") int id) {
		HttpStatus httpStatus = null;
		FoodModel foodModel = new FoodModel();
		try {
			foodModel = foodService.getFoodByIdAndActive(id);
			if (foodModel != null) {
				httpStatus = HttpStatus.OK;
				foodModel.setView(foodModel.getView() + 1);
				foodService.editFood(foodModel);
			} else {
				httpStatus = HttpStatus.NO_CONTENT;
			}

		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModel, httpStatus);
	}

	// lay cac mon an con actice theo shop
	@RequestMapping(value = "/getFoodAndActive/{id}")
	public ResponseEntity<Object> getFoodAndActive(@PathVariable("id") int id) {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getFoodByShopIdAndActive(id, false);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
	}

	// lay mon an (ke ca da active = false) theo ten
	@RequestMapping(value = "/getFoodByNameFood/{nameFood}")
	public ResponseEntity<Object> getFoodByNameFood(@PathVariable("nameFood") String nameFood) {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getListFoodByNameFood(nameFood);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
	}

	// lay cac mon an con actice theo ten
	@RequestMapping(value = "/getListFoodByNameContainingAndIsDelete/{nameFood}")
	public ResponseEntity<Object> getListFoodByNameContainingAndActive(@PathVariable("nameFood") String nameFood) {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getListFoodByNameContainingAndActive(nameFood, false);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
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

	// lay cac mon an con actice theo rating
	@RequestMapping(value = "/getFoodByOrderByViewAscAndIsDelete")
	public ResponseEntity<Object> getFoodByOrderByRateAsc() {
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

	// lay cac mon an con actice theo ngay dang
	@RequestMapping(value = "/getFoodByOrderByViewAscAndIsDelete")
	public ResponseEntity<Object> getFoodByCreatedAtAsc() {
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

	@RequestMapping(value = "/addFood", method = RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addFood(FoodModel foodModel) {
		HttpStatus httpStatus = null;
		try {
			foodService.addFood(foodModel);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}

		return new ResponseEntity<Object>(httpStatus);
	}

	// Sua thong tin food
	@PutMapping(value = "/editFood", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> editFood(FoodModel foodModel) {

		HttpStatus httpStatus = null;
		try {
			foodService.editFood(foodModel);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}

		return new ResponseEntity<Object>(httpStatus);
	}

}
