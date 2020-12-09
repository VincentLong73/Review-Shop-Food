package com.soict.reviewshopfood.service;

import com.soict.reviewshopfood.model.RateModel;
import com.soict.reviewshopfood.model.RatingOfFood;

public interface IRateService {
	void addRate(RateModel rateModel);
	RatingOfFood getListRateByFoodId(int foodId);
}
