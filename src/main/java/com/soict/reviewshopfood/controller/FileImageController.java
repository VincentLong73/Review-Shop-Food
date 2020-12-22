package com.soict.reviewshopfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.model.ImageAvatarModel;
import com.soict.reviewshopfood.model.ImageFoodModel;
import com.soict.reviewshopfood.service.impl.ImageAvatarService;
import com.soict.reviewshopfood.service.impl.ImageFoodService;


@RestController
@RequestMapping(value="/api/image")
public class FileImageController {
	
	@Autowired
	private ImageAvatarService iImageAvatarService;
	@Autowired
	private ImageFoodService imageFoodService;
	
	@PostMapping("/uploadImageAvatar/{userId}")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("userId") int userId) {
		HttpStatus httpStatus = null;
		try {
			iImageAvatarService.storeFileImageAvatar(file,userId);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Object>(httpStatus);
	}
	
	@PostMapping("/uploadImageFood/{foodId}")
	public ResponseEntity<Object> uploadMultiFiles(@RequestParam("files") MultipartFile files[],@PathVariable("foodId") int foodId){
		HttpStatus httpStatus = null;
		try {
			imageFoodService.storeFileImageFood(files,foodId);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Object>(httpStatus);
	}
	
	@GetMapping("/getImageAvatar/{id}")
	public ResponseEntity<ImageAvatarModel> getImageAvatar(@PathVariable int id){	
		HttpStatus httpStatus = null;
		ImageAvatarModel imageAvatarModel = null;
		try {
			imageAvatarModel = iImageAvatarService.getImageAvatar(id);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<ImageAvatarModel>(imageAvatarModel,httpStatus);			
	}
	
	@GetMapping("/getImageFood/{foodId}")
	public ResponseEntity<Object> getImageFood(@PathVariable int foodId){	
		HttpStatus httpStatus = null;
		List<ImageFoodModel> listImageFood = new ArrayList<ImageFoodModel>();
		try {
			listImageFood = imageFoodService.getImageFood(foodId);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(listImageFood,httpStatus);				
	}

}
