package com.soict.reviewshopfood.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.model.FoodModel;

@Service
public interface IFoodService {
	
	List<FoodModel> getListFood();
	void addFood(FoodModel foodModel);
	void editFood(FoodModel foodModel);
	FoodModel getFoodByShopId(int shopId);
	List<FoodModel> getListFoodByNameFood(String nameFood);
	void deleteFood(int id);

}
