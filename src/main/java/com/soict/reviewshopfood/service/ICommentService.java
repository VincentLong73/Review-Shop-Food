package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import com.soict.reviewshopfood.model.CommentModel;

public interface ICommentService {

	CommentModel addComment(CommentModel commentModel) throws SQLException; 			//Them comment vao bai viet cua mon an
	//void deleteComment(int id);
	List<CommentModel> getListCommentbyIdFood(int foodId) throws SQLException; //Lay cac comment theo mon an;
}
