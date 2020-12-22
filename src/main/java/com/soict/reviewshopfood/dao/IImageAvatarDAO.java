package com.soict.reviewshopfood.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.ImageAvatar;

@Repository
@Transactional
public interface IImageAvatarDAO extends JpaRepository<ImageAvatar, Integer>{

	//@Query("select image from ImageAvatar image where ")
	ImageAvatar findByUserId(int userId);
}
