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

	List<Food> getFoodByShopId(int shopId);
	List<Food> getFoodByShopIdAndIsDelete(int shopId,boolean isDelete);
	List<Food> getListFoodByNameContaining(String nameFood);
	List<Food> getListFoodByNameContainingAndIsDelete(String nameFood,boolean isDelete);
	@Query("select food from Food food where food.isDelete = false order by food.view")
	List<Food> getFoodByOrderByViewAsc();
}
