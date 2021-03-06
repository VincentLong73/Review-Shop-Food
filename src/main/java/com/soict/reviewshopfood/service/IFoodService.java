package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import com.soict.reviewshopfood.entity.Food;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.model.FoodModel;

@Service
public interface IFoodService {
	
	List<FoodModel> getListFoodByRate() throws SQLException; 					// Lay cac mon an theo so luong rating
	List<FoodModel> getFoodByCreatedAtDesc() throws SQLException; 					// Lay cac mon an theo so ngay dang
	List<FoodModel> getListFood() throws SQLException; 							//Lay danh sach cac mon an;
	Food addFood(FoodModel foodModel) throws SQLException; 						//Them mon an vao shop;
	void editFood(FoodModel foodModel) throws SQLException; 					//Sua thong tin cua mon an;
	List<FoodModel> getFoodByShopId(int shopId) throws SQLException; 			//Lay danh sach mon an theo shopId;
	List<FoodModel> getListFoodByNameFood(String nameFood) throws SQLException; //Lay danh sach mon an theo ten
	void deleteFood(int id) throws SQLException;								//Xoa mon an khoi shop					
	List<FoodModel> getFoodByShopIdAndActive(int shopId,boolean active); 		// Lay cac mon an con active
	//Lay danh sach mon an theo ten con active
	List<FoodModel> getListFoodByNameContainingAndActive(String nameFood,boolean active) throws SQLException; 
	List<FoodModel> getFoodByView() throws SQLException; 						// Lay cac mon an theo so luong view
	FoodModel getFoodByIdAndActive(int id) throws SQLException; 	//lay mon an theo id con ban
}
