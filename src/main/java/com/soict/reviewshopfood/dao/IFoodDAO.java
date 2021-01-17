package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.soict.reviewshopfood.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Food;

@Transactional
@Repository
public interface IFoodDAO extends JpaRepository<Food, Integer> {

	Food getFoodByIdAndIsDelete(int id,boolean isDelete);
	@Query(nativeQuery = true, value="select * from food where shop_id = :shopId and is_delete = 0 order by rate desc limit 20")
	List<Food> getFoodByShopId(int shopId);
	List<Food> getFoodByShopIdAndIsDelete(int shopId,boolean isDelete);
	List<Food> getListFoodByNameContaining(String nameFood);
	Food getFoodById(int id);
	List<Food> getListFoodByNameContainingAndIsDelete(String nameFood,boolean isDelete);
	@Query(nativeQuery = true, value="select * from Food food where is_delete = false order by view desc limit 20")
	List<Food> getFoodByOrderByViewDesc();
	@Query(nativeQuery = true, value="select * from Food food where is_delete = false order by created_at desc limit 20")
	List<Food> getFoodByCreatedAtDesc();
	@Query(nativeQuery = true, value="select * from food where is_delete = false order by rate desc limit 20")
	List<Food> getFoodByOrderByRateDesc();
	@Query(nativeQuery = true, value="select COUNT(id) from food")
	long getTotalFood();
	@Query(nativeQuery = true, value="select * from food WHERE name LIKE %:key%")
	List<Food> getFoodLike(String key);
	@Query(nativeQuery = true, value="select * from food WHERE name LIKE %:key% AND is_delete = 0")
	List<Food> getFoodLikeAndActive(String key);
}
