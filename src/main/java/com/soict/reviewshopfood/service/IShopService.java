package com.soict.reviewshopfood.service;

import java.util.List;

import com.soict.reviewshopfood.model.ShopModel;

public interface IShopService {

	List<ShopModel> findShopByNameShop(String nameShop);
	List<ShopModel> getListShop();
	ShopModel findShopById(int id);
	void addShop(ShopModel shopModel);
	void editShop(ShopModel shopModel);
	void deleteShop(int id);
	
	
}
