package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import com.soict.reviewshopfood.model.RateModel;
import com.soict.reviewshopfood.model.RatingOfFood;

public interface IRateService {
	void addRate(RateModel rateModel) throws SQLException; 				//Them danh gia cho mon an
	RatingOfFood getListRateByFoodId(int foodId) throws SQLException; //Lay danh sach cac danh gia theo tung loai va diem trung binh
}
