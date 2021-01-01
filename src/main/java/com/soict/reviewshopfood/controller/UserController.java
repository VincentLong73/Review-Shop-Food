package com.soict.reviewshopfood.controller;


import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.impl.ImageAvatarService;
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

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired

	private ImageAvatarService iImageAvatarService;

	//Lay thong tin user
	@GetMapping(value = "/getUserByEmail")
	public ResponseEntity<Object> getUserByEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();

		if(auth.getName() != null) {
			try {
				userModel = userService.findByEmailAfterLogin(auth.getName());
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.println(e);
			}
		}
		return new ResponseEntity<Object>(userModel, httpStatus);

	}

	// edit user
	@PutMapping(value = "/editUser", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> editUser(UserModel userModel) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = null;
		try {
			if (userService.editUser(userModel, auth.getName())) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}

	// upload anh avatar
//	@PostMapping("/uploadImageAvatar")
//	public ResponseEntity<String> uploadImageAvatar(@RequestParam("file") MultipartFile file) throws SQLException {
//
//		HttpStatus httpStatus = null;
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		try {
//			iImageAvatarService.storeFileImageAvatar(file, auth.getName());
//			httpStatus = HttpStatus.OK;
//		} catch (Exception e) {
//			System.out.println(e);
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		String imageUrl = iImageAvatarService.getImageAvatar(auth.getName());
//
//		return new ResponseEntity<String>(imageUrl,httpStatus);
//	}

	// lay anh tra ve fileName
//	@GetMapping("/getImageAvatar")
//	public ResponseEntity<Object> getImageAvatar1(HttpServletRequest request) throws SQLException {
//		String imageUrl = null;
//		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		try {
//			imageUrl = iImageAvatarService.getImageAvatar(auth.getName());
//			httpStatus = HttpStatus.OK;
//		} catch (Exception e) {
//			System.out.println(e);
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//
//		return new  ResponseEntity<Object>(imageUrl,httpStatus);
//	}

//	@Autowired
//	private UserService userService;
	// Cap nhat lai password
//	@PutMapping(value = "/applyNewPassword",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public ResponseEntity<Object> applyNewPassword( User user) {
//		HttpStatus httpStatus = null;
//		try {
//			userService.applyNewPassword(user);
//			httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			System.out.println(e);
//		}
//		return new ResponseEntity<Object>(httpStatus);
//	}

	// Get user
	@GetMapping(value = "/getUser/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") int id) {
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();
		try {
			userModel = userService.getUserById(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(userModel, httpStatus);
	}

	@PostMapping(value = "/uploadImageAvatar")
	public ResponseEntity<String> uploadImageAvatar(@RequestParam("file") MultipartFile file) throws SQLException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String urlImage = "";
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String token = String.valueOf(timestamp.getTime());
			String fileName = token.concat(Objects.requireNonNull(file.getOriginalFilename()));
			Path path = Paths.get("uploads/avatar/");
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			iImageAvatarService.saveFile(fileName, auth.getName());
			urlImage = fileName;
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.getStackTrace();
		}

		return new ResponseEntity<String>(urlImage, httpStatus);
	}

	@GetMapping("/avatar/{photo}")
	public ResponseEntity<Object> getImageAvatar1(@PathVariable("photo") String photo) throws SQLException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			Path filename = Paths.get("uploads/avatar/", photo);
			byte[] buffer = Files.readAllBytes(filename);
			ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
			return ResponseEntity.ok().contentLength(buffer.length).contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE)).body(byteArrayResource);
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(httpStatus);
	}
}
