package com.soict.reviewshopfood.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soict.reviewshopfood.entity.Comment;

public class CommentDAO {
	
	@PersistenceContext
	EntityManager entityManager; 
	
	public List<Comment> getListCommentByFoodId(int foodId){
		
		return entityManager.createQuery("select comment from Comment comment where comment.food.id = ?1", Comment.class)
				.setParameter(1,foodId).getResultList();
		
	}
	

}
