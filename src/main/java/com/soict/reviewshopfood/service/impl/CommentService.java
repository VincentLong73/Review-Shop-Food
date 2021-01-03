package com.soict.reviewshopfood.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.ICommentDAO;
import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.ILikeDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.Comment;
import com.soict.reviewshopfood.entity.Liked;
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
	
//	@Autowired
//	private LikeService likeService;
	
	@Autowired
	private ILikeDAO likeDao;

	@Override
	public void addComment(CommentModel commentModel) {
		if(commentModel != null && foodDao.existsById(commentModel.getFoodId())) {
			Comment comment = new Comment();
			comment.setRate(commentModel.getRate());
			comment.setContent(commentModel.getContent());
			comment.setCreatedAt(new Date());
			comment.setUser(userDao.getOne(commentModel.getUserId()));
			comment.setFood(foodDao.getOne(commentModel.getFoodId()));
			if(commentModel.getCommentParentId()!=0) {
				comment.setComment(commentDao.getOne(commentModel.getCommentParentId()));
			}
			commentDao.save(comment);
		}
	}

	@Override
	public List<CommentModel> getListCommentbyIdFood(int foodId) {
		List<CommentModel> commentModels = new ArrayList<>();
		if(foodDao.existsById(foodId)) {
			List<Comment> comments = commentDao.getListCommentByFoodId(foodId);
			
			for(Comment comment : comments) {
				CommentModel commentModel = new CommentModel();
				//List<LikeModel> listLike = likeService.getLike(comment.getId());
				List<Liked> listLike = likeDao.getLikedByCommentId(comment.getId());
				List<LikeModel> listLikeModel = new ArrayList<LikeModel>();
				if(listLike != null) {
					for(Liked like : listLike) {
						LikeModel likeModel = new LikeModel();
						likeModel.setId(like.getId());
						likeModel.setUserId(like.getUser().getId());
						likeModel.setCommentId(like.getComment().getId());
						listLikeModel.add(likeModel);
					}
				}
				
				commentModel.setCountLike(listLikeModel.size());
				commentModel.setListLike(listLikeModel);
				commentModel.setId(comment.getId());
				commentModel.setRate(comment.getRate());
				commentModel.setContent(comment.getContent());
				commentModel.setCreatedAt(comment.getCreatedAt());
				commentModel.setUserId(comment.getUser().getId());
				commentModel.setFoodId(comment.getFood().getId());
				
				if(comment.getComment() != null) {	
					commentModel.setCommentParentId(comment.getComment().getId());
				}
				commentModels.add(commentModel);
			}
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
