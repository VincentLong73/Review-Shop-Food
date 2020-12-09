package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Food;

@Transactional
@Repository
public interface IFoodDAO extends JpaRepository<Food, Integer> {

	List<Food> getFoodByShopId(int shopId);
	List<Food> getListFoodByNameContaining(String nameFood);
}
