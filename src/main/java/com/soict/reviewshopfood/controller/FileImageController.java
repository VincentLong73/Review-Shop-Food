package com.soict.reviewshopfood.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.service.IUserService;
import com.soict.reviewshopfood.service.impl.ImageAvatarService;
import com.soict.reviewshopfood.service.impl.ImageFoodService;

@RestController
@RequestMapping(value = "/api/image")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FileImageController {

	private static final Logger logger = LoggerFactory.getLogger(FileImageController.class);

	@Autowired
	private ImageAvatarService iImageAvatarService;
	@Autowired
	private ImageFoodService imageFoodService;
	@Autowired
	private IUserService userService;

	@PostMapping("/uploadImageAvatar")
	public ResponseEntity<Object> uploadImageAvatar(@RequestParam("file") MultipartFile file) {
		HttpStatus httpStatus = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			iImageAvatarService.storeFileImageAvatar(file, auth.getName());
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(httpStatus);
	}

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

	// lay anh tra ve fileName
	@GetMapping("/getImageAvatar")
	public ResponseEntity<Object> getImageAvatar1( HttpServletRequest request) throws SQLException {
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName() != null) {
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<Object>(userService.findByEmail(auth.getName()).getImageUrl(),httpStatus);
	}

	// lay anh theo userId
	@GetMapping("/getImageAvatar/{userId}")
	public ResponseEntity<Resource> getImageAvatar(@PathVariable("userId") int userId, HttpServletRequest request) {
		// HttpStatus httpStatus = null;
		Resource resource = null;
		try {
			resource = iImageAvatarService.getImageAvatar(userId);
			// httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			// httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	// lay anh theo fileName
	@GetMapping("/getImageAvatar")
	public ResponseEntity<Resource> getImageAvatarByFileName(HttpServletRequest request) {
		// HttpStatus httpStatus = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Resource resource = null;
		try {
			resource = iImageAvatarService.getImageAvatarByName(userService.findByEmail(auth.getName()).getImageUrl());
			// httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e);
			// httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/getListImageFoodId/{foodId}")
	public ResponseEntity<List<Integer>> getListImageFoodId(@PathVariable("foodId") int foodId) {
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		List<Integer> listImageFoodId = new ArrayList<Integer>();
		try {
			listImageFoodId = imageFoodService.getListIdImageFood(foodId);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<List<Integer>>(listImageFoodId, httpStatus);
	}

	@GetMapping("/getImageFood/{imageId}")
	public ResponseEntity<Resource> getImageFood(@PathVariable int imageId, HttpServletRequest request) {
		Resource resource = null;
		try {
			resource = imageFoodService.getImageFood(imageId);
		} catch (Exception e) {
			System.out.println(e);
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
