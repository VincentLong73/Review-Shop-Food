package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.ImageFood;

@Repository
@Transactional
public interface IImageFoodDAO extends JpaRepository<ImageFood, Integer>{
	List<ImageFood> findByFoodId(int foodId);
}
