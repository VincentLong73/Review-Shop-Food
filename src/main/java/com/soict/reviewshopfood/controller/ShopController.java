package com.soict.reviewshopfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value="/review-shop-food")
public class ShopController {

	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value="/getShop/{id}")
	public ResponseEntity<ShopModel> getShopById(@PathVariable("id") int id){
		
		return new ResponseEntity<ShopModel>(shopService.findShopById(id), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getShopByNameShop/{nameShop}")
	public ResponseEntity<ShopModel> getShopByNameShop(@PathVariable("nameShop") String nameShop){
	
		return new ResponseEntity<ShopModel>(shopService.findShopByNameShop(nameShop), HttpStatus.OK);
	}
	
	@RequestMapping(value="/addShop", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addShop(@RequestBody ShopModel shopModel){
		
		shopService.addShop(shopModel);
		return new ResponseEntity<Object>("Add successfully!", HttpStatus.OK);
		
		
	}
	
	@PutMapping(value="/editShop" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editShop(@RequestBody ShopModel shopModel){
		
		shopService.editShop(shopModel);
		
		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
		
		
	}
	
	@DeleteMapping(value="/deleteShop/{id}")
	public ResponseEntity<Object> deleteShop(@PathVariable("id") int id){
		
		shopService.deleteShop(id);
		
		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}
}
