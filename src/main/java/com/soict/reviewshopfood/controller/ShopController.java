package com.soict.reviewshopfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.ShopModel;
import com.soict.reviewshopfood.service.impl.ShopService;

@RestController
@RequestMapping(value="/api/shop")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ShopController {

	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value="/getShop/{id}")
	public ResponseEntity<Object> getShopById(@PathVariable("id") int id){
		HttpStatus httpStatus = null;
		ShopModel shopModel = null;
		try {
			shopModel = shopService.findShopById(id);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(shopModel, httpStatus);
	}
	
	@RequestMapping(value="/getShopByNameShop/{nameShop}")
	public ResponseEntity<Object> getShopByNameShop(@PathVariable("nameShop") String nameShop){
		HttpStatus httpStatus = null;
		List<ShopModel> shopModels = new ArrayList<ShopModel>();
		try {
			shopModels = shopService.findShopByNameShop(nameShop);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(shopModels, httpStatus);
	}
	
	@RequestMapping(value="/addShop", method = RequestMethod.POST ,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addShop(ShopModel shopModel){
		HttpStatus httpStatus = null;
		try {
			shopService.addShop(shopModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<Object>(httpStatus);
	}
	
	@PutMapping(value="/editShop" ,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> editShop(@RequestBody ShopModel shopModel){
		HttpStatus httpStatus = null;
		try {
			shopService.editShop(shopModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<Object>(httpStatus);
	}
	
	@DeleteMapping(value="/deleteShop",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteShop(int id){
		
		HttpStatus httpStatus = null;
		try {
			shopService.deleteShop(id);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<Object>(httpStatus);
	}
}
