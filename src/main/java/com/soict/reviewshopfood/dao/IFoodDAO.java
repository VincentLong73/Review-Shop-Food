package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Food;

@Transactional
@Repository
public interface IFoodDAO extends JpaRepository<Food, Integer> {

	Food getFoodByIdAndIsDelete(int id,boolean isDelete);
	List<Food> getFoodByShopId(int shopId);
	List<Food> getFoodByShopIdAndIsDelete(int shopId,boolean isDelete);
	List<Food> getListFoodByNameContaining(String nameFood);
	Food getFoodById(int id);
	List<Food> getListFoodByNameContainingAndIsDelete(String nameFood,boolean isDelete);
	@Query("select food from Food food where food.isDelete = false order by food.view")
	List<Food> getFoodByOrderByViewDesc();
	@Query("select food from Food food where food.isDelete = false order by food.createdAt DESC")
	List<Food> getFoodByCreatedAtAsc();
	@Query("select food from Food food where food.isDelete = false order by food.rate DESC")
	List<Food> getFoodByOrderByRateDesc();
	
}
