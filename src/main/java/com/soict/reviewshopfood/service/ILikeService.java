package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import com.soict.reviewshopfood.model.LikeModel;

public interface ILikeService {
	void addLike(int commentId, int userId) throws SQLException; //Them like vao db
	List<LikeModel> getLike(int commentId);
}
