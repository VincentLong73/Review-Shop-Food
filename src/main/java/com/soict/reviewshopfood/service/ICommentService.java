package com.soict.reviewshopfood.service;

import java.util.List;

import com.soict.reviewshopfood.model.CommentModel;

public interface ICommentService {

	void addComment(CommentModel commentModel);
	
	//void deleteComment(int id);
	
	List<CommentModel> getListCommentbyIdFood(int foodId);
}
