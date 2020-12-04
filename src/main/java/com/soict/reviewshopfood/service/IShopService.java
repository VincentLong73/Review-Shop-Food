package com.soict.reviewshopfood.service;

import com.soict.reviewshopfood.model.ShopModel;

public interface IShopService {

	ShopModel findShopByNameShop(String nameShop);
	
	ShopModel findShopById(int id);
	
	void addShop(ShopModel shopModel);
	
	void editShop(ShopModel shopModel);
	
	void deleteShop(int id);
	
	
}
