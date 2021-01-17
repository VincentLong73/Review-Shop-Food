package com.soict.reviewshopfood.controller;

import com.soict.reviewshopfood.model.ChildCommentForm;
import com.soict.reviewshopfood.model.CommentModel;
import com.soict.reviewshopfood.model.LikeModel;
import com.soict.reviewshopfood.service.impl.CommentService;
import com.soict.reviewshopfood.service.impl.LikeService;
import com.soict.reviewshopfood.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comment")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/getListComment/{foodId}")
	public ResponseEntity<Object> getListCommentByIdFood(@PathVariable("foodId") int foodId) {
		HttpStatus httpStatus = null;
		List<CommentModel> commentModels = new ArrayList<CommentModel>();
		try {
			commentModels = commentService.getListCommentbyIdFood(foodId);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(commentModels, httpStatus);
	}
	
	@PostMapping(value="/addComment",produces = {  MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> addComment(CommentModel commentModel){
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			commentModel = commentService.addComment(commentModel);
			httpStatus = HttpStatus.CREATED;
		}catch(Exception e) {
			e.getStackTrace();
		}
		return new ResponseEntity<Object>(commentModel, httpStatus);
	}

	@PostMapping(value="/addLike",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addLike(LikeModel likeModel){
		HttpStatus httpStatus = null;
		try {
			likeService.addLike(likeModel.getCommentId(), likeModel.getUserId());
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	@GetMapping(value = "/getChildComment/{commentId}")
	public ResponseEntity<Object> getChildComment(@PathVariable("commentId") int commentId) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<CommentModel> commentModels = null;
		try {
			commentModels = commentService.getChildComment(commentId);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return new ResponseEntity<Object>(commentModels, httpStatus);
	}

	//	@DeleteMapping(value="deleteComment/{id}")
//	public ResponseEntity<Object> deleteComment(@PathVariable("id") int id){
//		
//		
//		
//		return new ResponseEntity<Object>("Delete comment successfully!",HttpStatus.OK);
//	}
	@PostMapping(value = "/addChildComment", produces = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> addChildComment(ChildCommentForm childCommentForm) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		CommentModel commentModel = null;
		if (auth != null) {
			try {
				commentModel = commentService.addChildComment(childCommentForm, userService.findByEmail(auth.getName()));
				httpStatus = HttpStatus.CREATED;
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		return new ResponseEntity<Object>(commentModel, httpStatus);
	}
}
