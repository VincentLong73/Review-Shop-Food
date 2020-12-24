package com.soict.reviewshopfood.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.ICommentDAO;
import com.soict.reviewshopfood.dao.ILikeDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.Like;
import com.soict.reviewshopfood.model.LikeModel;
import com.soict.reviewshopfood.service.ILikeService;
@Service
public class LikeService implements ILikeService{
	
	@Autowired
	private IUserDAO userDao;
	@Autowired
	private ICommentDAO commentDao;
	@Autowired
	private ILikeDAO likeDao;

	@Override
	public void addLike(LikeModel likeModel) throws SQLException {
		if(likeModel.getUserId()  != 0 && likeModel.getCommentId() != 0) {
			Like like = new Like();
			like.setUser(userDao.getOne(likeModel.getUserId()));
			like.setComment(commentDao.getOne(likeModel.getCommentId()));
			likeDao.save(like);
		}
		
	}

	@Override
	public List<LikeModel> getLike(int commentId) {
		List<Like> listLike = likeDao.getListLikeByCommentId(commentId);
		List<LikeModel> listLikeModel = new ArrayList<LikeModel>();
		if(listLike != null) {
			for(Like like : listLike) {
				LikeModel likeModel = new LikeModel();
				likeModel.setId(like.getId());
				likeModel.setUserId(like.getUser().getId());
				likeModel.setCommentId(like.getComment().getId());
				listLikeModel.add(likeModel);
			}
		}
		return listLikeModel;
	}

}
