package com.soict.reviewshopfood.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.model.FoodModel;

@Service
public interface IFoodService {
	
	void addFood(FoodModel foodModel);
	void editFood(FoodModel foodModel);
	List<FoodModel> getListFoodByShopId(int shopId);
	void deleteFood(int id);

}
