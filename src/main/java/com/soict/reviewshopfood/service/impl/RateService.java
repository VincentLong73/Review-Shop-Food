package com.soict.reviewshopfood.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.IRateDAO;
import com.soict.reviewshopfood.entity.Rate;
import com.soict.reviewshopfood.model.RateModel;
import com.soict.reviewshopfood.model.RatingOfFood;
import com.soict.reviewshopfood.service.IRateService;

@Service
public class RateService implements IRateService{
	
	@Autowired
	private IRateDAO rateDao;
	@Autowired
	private IFoodDAO foodDao;

	@Override
	public void addRate(RateModel rateModel) {//them diem rate vao tung mon an
		if(rateModel != null) {
			Rate rate = new Rate();
			rate.setFood(foodDao.getOne(rateModel.getFoodId()));
			rate.setMarkRate(rateModel.getMarkRate());
			rateDao.save(rate);
		}
	}

	@Override
	public RatingOfFood getListRateByFoodId(int foodId) {//lay cac diem rate theo tung food
		List<Rate> rates = rateDao.getRateByFoodId(foodId);
		List<Integer> listRate = new ArrayList<Integer>();
		int sumRate = 0;
		for(Rate rate : rates) {
			listRate.add(rate.getMarkRate());
			sumRate += rate.getMarkRate();
		}
		RatingOfFood rating = new RatingOfFood();
		rating.setFoodId(foodId);
		rating.setListRate(listRate);
		// Tinh rating tu cac diem rate cua food
		rating.setRating((double) Math.round(((double)sumRate/listRate.size()) * 10) / 10);
		return rating;
	}

}
