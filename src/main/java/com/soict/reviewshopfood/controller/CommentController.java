package com.soict.reviewshopfood.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soict.reviewshopfood.model.CommentModel;
import com.soict.reviewshopfood.service.impl.CommentService;

@RestController
@RequestMapping(value="/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping(value="/getListComment/{foodId}")
	public ResponseEntity<Object> getListCommentByIdFood(@PathVariable("foodId") int foodId){
		HttpStatus httpStatus = null;
		List<CommentModel> commentModels = new ArrayList<CommentModel>();
		try {
			commentModels = commentService.getListCommentbyIdFood(foodId);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(commentModels,httpStatus);
	}
	
	@PostMapping(value="/addComment",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addComment(CommentModel commentModel){
		HttpStatus httpStatus = null;
		try {
			commentService.addComment(commentModel);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	
//	@DeleteMapping(value="deleteComment/{id}")
//	public ResponseEntity<Object> deleteComment(@PathVariable("id") int id){
//		
//		
//		
//		return new ResponseEntity<Object>("Delete comment successfully!",HttpStatus.OK);
//	}

}
