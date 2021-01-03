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
				
				List<Liked> listLike = likeDao.getLikedByCommentId(comment.getId());
				List<LikeModel> listLikeModel = new ArrayList<LikeModel>();
				listLikeModel = mapListLike(listLike);

				commentModel = mapComment(comment);
				
				commentModel.setCountLike(listLikeModel.size());
				commentModel.setListLike(listLikeModel);
				commentModel.setRate(comment.getRate());
				
				if(comment.getComment() != null && commentDao.existsById(comment.getComment().getId())) {	
					Comment commentParent = commentDao.getOne(comment.getComment().getId());
					CommentModel commentParentModel = new CommentModel();
					
					commentParentModel = mapComment(commentParent);
					List<LikeModel> listLikeModel1 = new ArrayList<LikeModel>();
					listLikeModel1 = mapListLike(likeDao.getLikedByCommentId(commentParentModel.getId()));
					commentParentModel.setCountLike(listLikeModel1.size());
					commentParentModel.setListLike(listLikeModel1);
					
					commentModel.setCommentParent(commentParentModel);
					
				}
				commentModels.add(commentModel);
			}
		}
		
		return commentModels;
	}

	private List<LikeModel> mapListLike(List<Liked> likedByCommentId) {
		List<LikeModel> listLikeModel = new ArrayList<LikeModel>();
		if(likedByCommentId != null) {
			for(Liked like : likedByCommentId) {
				LikeModel likeModel = new LikeModel();
				likeModel.setId(like.getId());
				likeModel.setUserId(like.getUser().getId());
				likeModel.setUserName(like.getUser().getUserName());
				listLikeModel.add(likeModel);
			}
		}
		return listLikeModel;
	}

	private CommentModel mapComment(Comment comment) {
		CommentModel commentModel = new CommentModel();
		commentModel.setId(comment.getId());
		commentModel.setContent(comment.getContent());
		commentModel.setCreatedAt(comment.getCreatedAt());
		commentModel.setUserId(comment.getUser().getId());
		commentModel.setFoodId(comment.getFood().getId());
		commentModel.setUserName(comment.getUser().getUserName());
		return commentModel;
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
