package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.entity.Shop;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.*;
import com.soict.reviewshopfood.service.impl.AddressService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		UserModel user = null;
		try {
			ShopModel shopModel = shopService.findShopById(id);
			user = userService.getUserById(shopModel.getUserId());
			user.setShopModel(shopModel);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(user, httpStatus);
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
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}

		return new ResponseEntity<Object>(httpStatus);
	}

//	@PutMapping(value="/editShop" ,produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public ResponseEntity<Object> editShop(@RequestBody ShopModel shopModel){
//		HttpStatus httpStatus = null;
//		try {
//			shopService.editShop(shopModel);
//			httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			System.out.println(e);
//		}
//
//		return new ResponseEntity<Object>(httpStatus);
//	}

	@DeleteMapping(value = "/deleteShop", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteShop(int id) {

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			shopService.deleteShop(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {

			e.getStackTrace();
		}

		return new ResponseEntity<Object>(httpStatus);
	}

	@GetMapping(value = "/getShopInfo")
	public ResponseEntity<Object> getShopInfo() {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		UserModel userModel = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				User user = userService.findByEmail(auth.getName());
				ShopModel shopModel = shopService.findShopById(user.getShop().getId());
				userModel = modelMapper.map(user, UserModel.class);
				userModel.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/user/avatar/" + user.getImageUrl()).toUriString());
				userModel.setShopModel(shopModel);
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(userModel, httpStatus);
	}

	@PostMapping(value = "/editShop", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> editShop(FormEditShop formEditShop) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		ShopModel shopModel = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			try {
				Shop shop = shopService.editShop(formEditShop, auth.getName());
				shopModel = modelMapper.map(shop, ShopModel.class);
				shopModel.setAddressModel(modelMapper.map(shop.getAddress(), AddressModel.class));
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(shopModel, httpStatus);
	}

}
