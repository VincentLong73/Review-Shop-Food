package com.soict.reviewshopfood.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.IShopDAO;
import com.soict.reviewshopfood.entity.Food;
import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.service.IFoodService;

@Service
public class FoodService implements IFoodService {
	
	@Autowired
	private IFoodDAO foodDao;
	
	@Autowired
	private IShopDAO shopDao;

	@Override
	public void addFood(FoodModel foodModel) {
		
		Food food = new Food();
		
		food.setName(foodModel.getName());
		food.setContent(foodModel.getContent());
		food.setPrice(foodModel.getPrice());
		food.setCreatedAt(new Date());
		food.setImage(foodModel.getName());
		food.setCreatedBy(foodModel.getCreatedBy());
		food.setShop(shopDao.getOne(foodModel.getShopId()));
		
		
		foodDao.saveAndFlush(food);
		
		
	}

	@Override
	public void editFood(FoodModel foodModel) {
		
		Food food = foodDao.getOne(foodModel.getId());
		
		food.setName(foodModel.getName());
		food.setContent(foodModel.getContent());
		food.setPrice(foodModel.getPrice());
		food.setImage(foodModel.getImage());
		
		foodDao.save(food);
		
	}

	@Override
	public void deleteFood(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FoodModel> getListFoodByShopId(int shopId) {
		
		List<Food> foods = foodDao.getListFoodByShopId(shopId);
		List<FoodModel> foodModels = new ArrayList<>();
		
		for(Food food : foods) {
			
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setImage(food.getImage());
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(shopId);
			
			foodModels.add(foodModel);
			
		}
		return foodModels;
	}

}
