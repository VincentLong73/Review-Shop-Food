package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.entity.Food;
import com.soict.reviewshopfood.entity.Shop;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.model.FormNewFood;
import com.soict.reviewshopfood.service.impl.FoodService;
import com.soict.reviewshopfood.service.impl.ImageFoodService;
import com.soict.reviewshopfood.service.impl.ShopService;
import com.soict.reviewshopfood.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/food")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FoodController {

	@Autowired
	private FoodService foodService;
	@Autowired
	private ImageFoodService imageFoodService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;

	// lay mon an theo id va con ban
	@GetMapping(value = "/getFood/{id}")
	public ResponseEntity<Object> getFood(@PathVariable("id") int id) {
		HttpStatus httpStatus = null;
		FoodModel foodModel = new FoodModel();
		try {
			foodModel = foodService.getFoodByIdAndActive(id);
			if (foodModel != null) {
				httpStatus = HttpStatus.OK;
				foodModel.setView(foodModel.getView()+1);
				foodService.addCountView(foodModel);
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

	// lay cac mon an con actice theo so luong view, lay gioi han 20 mon
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

	// lay cac mon an con actice theo rating ,lay gioi han 20 mon
	@GetMapping(value = "/getFoodByOrderByRateDesc")
	public ResponseEntity<Object> getFoodByOrderByRateDesc() {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getListFoodByRate();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
	}

	// lay cac mon an con actice theo ngay dang , lay gioi han 20 mon
	@RequestMapping(value = "/getFoodByCreatedAtDesc")
	public ResponseEntity<Object> getFoodByCreatedAtDesc() {
		HttpStatus httpStatus = null;
		List<FoodModel> foodModels = new ArrayList<FoodModel>();
		try {
			foodModels = foodService.getFoodByCreatedAtDesc();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(foodModels, httpStatus);
	}

	//Them mon an
	@RequestMapping(value = "/addFood", method = RequestMethod.POST, produces = { MediaType.APPLICATION_FORM_URLENCODED_VALUE
																				, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Object> addFood(FoodModel foodModel) {
		HttpStatus httpStatus = null;

		try {
			if(foodService.addFood(foodModel)) {
				httpStatus = HttpStatus.OK;
			}else {
				httpStatus = HttpStatus.NO_CONTENT;
			}
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}

		return new ResponseEntity<Object>(httpStatus);
	}

	//Them anh cho mon an theo foodId
	@PostMapping("/uploadImageFood/{foodId}")
	public ResponseEntity<Object> uploadMultiFiles(@RequestParam("files") MultipartFile files[],
			@PathVariable("foodId") int foodId) {
		HttpStatus httpStatus = null;
		try {
			imageFoodService.storeFileImageFood(files, foodId);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
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

	//Them mon an moi
	@PostMapping(value = "/createNewFood", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Object> uploadFoodThumbnail(@RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam("foodImages") MultipartFile[] foodImages, FormNewFood formNewFood) throws SQLException {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Food food = null;
		FoodModel foodModel = null;
		if (auth.isAuthenticated()) {
			User user = userService.findByEmail(auth.getName());
			if (user.getRole().getCode().equals("ROLE_SHOP")) {
				try {
					Shop shop = shopService.findShopByUserId(user.getId());
					food = foodService.saveNewFood(thumbnail, foodImages, formNewFood, shop);
					httpStatus = HttpStatus.CREATED;
					foodModel = foodService.getFoodByIdAndActive(food.getId());
					if (foodModel != null) {
						foodModel.setView(foodModel.getView());
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return new ResponseEntity<Object>(foodModel, httpStatus);
	}
	@GetMapping("/foodImage/{photo}")
	public ResponseEntity<Object> getImageAvatar1(@PathVariable("photo") String photo) throws SQLException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			Path filename = Paths.get("uploads/foods/", photo);
			byte[] buffer = Files.readAllBytes(filename);
			ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
			return ResponseEntity.ok().contentLength(buffer.length).contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE)).body(byteArrayResource);
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(httpStatus);
	}

	@GetMapping(value = "/getFoodByShop/{idShop}")
	public Object getFoodByShop(@PathVariable("idShop") int idShop) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<FoodModel> foods = null;
		try {
			foods = foodService.getFoodByShopId(idShop);
			httpStatus = HttpStatus.OK;
		}catch (Exception e) {
			e.getStackTrace();
		}
		return new ResponseEntity<Object>(foods, httpStatus);
	}
}
