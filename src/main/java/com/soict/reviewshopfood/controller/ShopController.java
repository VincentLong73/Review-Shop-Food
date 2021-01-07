package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.model.ShopModel;
import com.soict.reviewshopfood.service.impl.AddressService;
import com.soict.reviewshopfood.service.impl.ShopService;
import com.soict.reviewshopfood.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/shop")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ShopController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AddressService addressService;

	@GetMapping(value = "/getShop/{id}")
	public ResponseEntity<Object> getShopById(@PathVariable("id") int id) {
		HttpStatus httpStatus = null;
		ShopModel shopModel = null;
		try {
			shopModel = shopService.findShopById(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(shopModel, httpStatus);
	}
	
	@GetMapping(value="/getShopByNameShop/{nameShop}")
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
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}

		return new ResponseEntity<Object>(httpStatus);
	}

}
