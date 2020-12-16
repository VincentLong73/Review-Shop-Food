package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import com.soict.reviewshopfood.model.ShopModel;

public interface IShopService {

	List<ShopModel> findShopByNameShop(String nameShop) throws SQLException;//Tim shop theo ten
	List<ShopModel> getListShop() throws SQLException;						//Lay danh sach shop;
	ShopModel findShopById(int id) throws SQLException;						//Tim shop theo id
	void addShop(ShopModel shopModel) throws SQLException;					//Them shop theo
	void editShop(ShopModel shopModel) throws SQLException;					//Sua thong tin shop theo
	void deleteShop(int id)  throws SQLException;							//Xoa shop theo id
	
	
}
