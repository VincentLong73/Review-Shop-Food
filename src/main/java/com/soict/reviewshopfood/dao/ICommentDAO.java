package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Comment;

@Transactional
@Repository
public interface ICommentDAO extends JpaRepository<Comment, Integer>{

	List<Comment> getListCommentByFoodId(int foodId);
	@Query(nativeQuery = true, value="select * from comment where parent_comment_id = :commentId")
	List<Comment> getListCommentByParentId(int commentId);
//	@Query("select * from Comment c where c.comment.id = ?1")
//	Comment getCommentByCommmentParentId(int id);
}
