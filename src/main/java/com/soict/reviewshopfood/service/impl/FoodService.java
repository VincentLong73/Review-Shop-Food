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
import com.soict.reviewshopfood.model.ImageFoodModel;
import com.soict.reviewshopfood.service.IFoodService;

@Service
public class FoodService implements IFoodService {
	
	@Autowired
	private IFoodDAO foodDao;
	@Autowired
	private IShopDAO shopDao;
	@Autowired
	private ImageFoodService imageFoodService;

	@Override
	public void addFood(FoodModel foodModel) {
		Food food = new Food();
		
		food.setName(foodModel.getName());
		food.setContent(foodModel.getContent());
		food.setPrice(foodModel.getPrice());
		food.setView(0);
		food.setCreatedAt(new Date());
		food.setDelete(false);
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
		food.setDelete(true);
		foodDao.saveAndFlush(food);
	}

	@Override
	public List<FoodModel> getFoodByShopId(int shopId) {
		List<Food> foods = foodDao.getFoodByShopId(shopId);
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	@Override
	public List<FoodModel> getListFoodByNameFood(String nameFood) {
		List<Food> foods = foodDao.getListFoodByNameContaining(nameFood);
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	@Override
	public List<FoodModel> getListFood() {
		List<Food> foods = foodDao.findAll();
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	@Override
	public List<FoodModel> getFoodByShopIdAndActive(int shopId, boolean active) {
		List<Food> foods = foodDao.getFoodByShopIdAndIsDelete(shopId, active);
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	@Override
	public List<FoodModel> getListFoodByNameContainingAndActive(String nameFood, boolean active) throws SQLException {
		List<Food> foods = foodDao.getListFoodByNameContainingAndIsDelete(nameFood, active);
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	@Override
	public List<FoodModel> getFoodByView() throws SQLException {
		List<Food> foods = foodDao.getFoodByOrderByViewAsc();
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}
	
	private List<FoodModel> getListFoodModel(List<Food> foods){
		List<FoodModel> foodModels = new ArrayList<>();
		for(Food food : foods) {
			FoodModel foodModel = new FoodModel();
			
			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setDelete(food.isDelete());
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setShopId(food.getShop().getId());
			foodModel.setImageFoodIds(getListImageFood(food.getId()));
			
			foodModels.add(foodModel);
		}
		return foodModels;
	}
	private List<Integer> getListImageFood(int foodId){
		List<ImageFoodModel> listImageFood = imageFoodService.getImageFood(foodId);
		List<Integer> listImageFoodId = new ArrayList<Integer>();
		for(ImageFoodModel image : listImageFood) {
			listImageFoodId.add(image.getId());
		}
		return listImageFoodId;
	}

}
