package com.soict.reviewshopfood.controller;

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
@RequestMapping(value="/review-shop-food")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping(value="/getListComment/{foodId}")
	public ResponseEntity<List<CommentModel>> getListCommentByIdFood(@PathVariable("foodId") int foodId){
		
		return new ResponseEntity<List<CommentModel>>(commentService.getListCommentbyIdFood(foodId),HttpStatus.OK);
	}
	
	@PostMapping(value="/addComment",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addComment(CommentModel commentModel){
		
		commentService.addComment(commentModel);
		
		
		return new ResponseEntity<Object>("Add Comment Successfully!",HttpStatus.OK);
	}
	
//	@DeleteMapping(value="deleteComment/{id}")
//	public ResponseEntity<Object> deleteComment(@PathVariable("id") int id){
//		
//		
//		
//		return new ResponseEntity<Object>("Delete comment successfully!",HttpStatus.OK);
//	}

}
