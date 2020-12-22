package com.soict.reviewshopfood.service.impl;

import java.sql.SQLException;
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
		food.setView(0);
		food.setCreatedAt(new Date());
		food.setActive(true);
		food.setCreatedBy(foodModel.getCreatedBy());
		food.setShop(shopDao.getOne(foodModel.getShopId()));
		
		foodDao.save(food);
		
		
	}

	@Override
	public void editFood(FoodModel foodModel) {
		
		Food food = foodDao.getOne(foodModel.getId());
		
		food.setName(foodModel.getName());
		food.setContent(foodModel.getContent());
		food.setPrice(foodModel.getPrice());
		foodDao.save(food);
		
	}

	@Override
	public void deleteFood(int id) {
		Food food = foodDao.getOne(id);
		food.setActive(false);
		foodDao.saveAndFlush(food);
	}

	@Override
	public List<FoodModel> getFoodByShopId(int shopId) {
		List<Food> foods = foodDao.getFoodByShopId(shopId);
		List<FoodModel> foodModels = new ArrayList<>();
		
		for(Food food : foods) {
			
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setActive(food.isActive());
			
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(food.getShop().getId());
			
			foodModels.add(foodModel);
			
		}
		return foodModels;
	}

	@Override
	public List<FoodModel> getListFoodByNameFood(String nameFood) {
		List<Food> foods = foodDao.getListFoodByNameContaining(nameFood);
		List<FoodModel> foodModels = new ArrayList<>();
		
		for(Food food : foods) {
			
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setActive(food.isActive());
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(food.getShop().getId());
			
			foodModels.add(foodModel);
			
		}
		return foodModels;
	}

	@Override
	public List<FoodModel> getListFood() {
		List<Food> foods = foodDao.findAll();
		List<FoodModel> foodModels = new ArrayList<>();
		
		for(Food food : foods) {
			
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setActive(food.isActive());
			
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(food.getShop().getId());
			
			foodModels.add(foodModel);
			
		}
		return foodModels;
	}

	@Override
	public List<FoodModel> getFoodByShopIdAndActive(int shopId, boolean active) {
		List<Food> foods = foodDao.getFoodByShopIdAndActive(shopId, active);
		List<FoodModel> foodModels = new ArrayList<>();
		
		for(Food food : foods) {
			
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setActive(food.isActive());
	
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(food.getShop().getId());
			
			foodModels.add(foodModel);
			
		}
		return foodModels;
	}

	@Override
	public List<FoodModel> getListFoodByNameContainingAndActive(String nameFood, boolean active) throws SQLException {
		List<Food> foods = foodDao.getListFoodByNameContainingAndActive(nameFood, active);
		List<FoodModel> foodModels = new ArrayList<>();
		
		for(Food food : foods) {
			
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setActive(food.isActive());
			
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(food.getShop().getId());
			
			foodModels.add(foodModel);
			
		}
		return foodModels;
	}

}
