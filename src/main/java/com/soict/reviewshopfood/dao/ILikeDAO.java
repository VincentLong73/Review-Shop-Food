package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Liked;

@Repository
@Transactional
public interface ILikeDAO extends JpaRepository<Liked, Integer>{
	
	List<Liked> getLikedByCommentId(int commentId);
}
