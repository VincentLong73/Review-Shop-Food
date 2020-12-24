package com.soict.reviewshopfood.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.ICommentDAO;
import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.Comment;
import com.soict.reviewshopfood.model.CommentModel;
import com.soict.reviewshopfood.model.LikeModel;
import com.soict.reviewshopfood.service.ICommentService;

@Service
public class CommentService implements ICommentService{
	
	@Autowired
	private IUserDAO userDao; 
	@Autowired
	private IFoodDAO foodDao;
	@Autowired
	private ICommentDAO commentDao;
	
	@Autowired
	private LikeService likeService;

	@Override
	public void addComment(CommentModel commentModel) {
		
		Comment comment = new Comment();
		
		comment.setContent(commentModel.getContent());
		comment.setCreatedAt(new Date());
		comment.setUser(userDao.getOne(commentModel.getUserId()));
		comment.setFood(foodDao.getOne(commentModel.getFoodId()));
		if(commentModel.getCommentParentId()!=0) {
			comment.setComment(commentDao.getOne(commentModel.getCommentParentId()));
		}
		commentDao.save(comment);
	}

	@Override
	public List<CommentModel> getListCommentbyIdFood(int foodId) {
		
		List<Comment> comments = commentDao.getListCommentByFoodId(foodId);
		List<CommentModel> commentModels = new ArrayList<>();
		
		for(Comment comment : comments) {
			CommentModel commentModel = new CommentModel();
			List<LikeModel> listLike = likeService.getLike(comment.getId());
			commentModel.setCountLike(listLike.size());
			commentModel.setListLike(listLike);
			commentModel.setId(comment.getId());
			commentModel.setContent(comment.getContent());
			commentModel.setCreatedAt(comment.getCreatedAt());
			commentModel.setUserId(comment.getUser().getId());
			commentModel.setFoodId(comment.getFood().getId());
			
			if(comment.getComment() != null) {	
				commentModel.setCommentParentId(comment.getComment().getId());
			}
			commentModels.add(commentModel);
		}
		
		return commentModels;
	}

//	@Override
//	public void deleteComment(int id) {
//		if(commentDao.getOne(id)!=null) {
//			if(commentDao.getCommentByCommmentParentId(id)!=null){
//				commentDao.delete(commentDao.getCommentByCommmentParentId(id));
//			}
//			commentDao.delete(commentDao.getOne(id));
//		}
//		
//	}


}
