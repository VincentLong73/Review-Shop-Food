package com.soict.reviewshopfood.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IAddressDAO;
import com.soict.reviewshopfood.dao.IShopDAO;
import com.soict.reviewshopfood.entity.Shop;
import com.soict.reviewshopfood.model.AddressModel;
import com.soict.reviewshopfood.model.ShopModel;
import com.soict.reviewshopfood.service.IShopService;

@Service
public class ShopService implements IShopService {

	@Autowired
	private IShopDAO shopDao;
	
	@Autowired
	private IAddressDAO addressDao;
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ShopModel findShopByNameShop(String nameShop) {

		List<Shop> shops = shopDao.findShopByNameShop(nameShop);
		ShopModel shopModel = new ShopModel();
//		if (shop != null) {
//			shopModel = modelMapper.map(shop, ShopModel.class);
//			shopModel.setAddressModel(modelMapper.map(shop.getAddress(), AddressModel.class));
//		}

		return shopModel;
	}

	@Override
	public void addShop(ShopModel shopModel) {

		Shop shop = modelMapper.map(shopModel, Shop.class);

		addressService.addAddress(shopModel.getAddressModel());
		
		AddressModel addressModel = shopModel.getAddressModel();
		
		shop.setAddress(addressDao.findByDistrictAndVillageAndStreet(addressModel.getDistrict(), addressModel.getVillage(),addressModel.getStreet()));

		shopDao.saveAndFlush(shop);
	}

	@Override
	public void editShop(ShopModel shopModel) {

		Shop shop = shopDao.getOne(shopModel.getId());
		
		shop = modelMapper.map(shopModel, Shop.class);
		
		addressService.editAddress(shopModel.getAddressModel());

		shop.setAddress(addressDao.getOne(shopModel.getAddressModel().getId()));

		shopDao.save(shop);

	}

	@Override
	public void deleteShop(int id) {

		if (shopDao.getOne(id) != null) {
			int addressId = shopDao.getOne(id).getAddress().getId();
			shopDao.deleteById(id);
			addressDao.deleteById(addressId);
			
		}

	}

	@Override
	public ShopModel findShopById(int id) {
		
		Shop shop = shopDao.getOne(id);
		ShopModel shopModel = new ShopModel();
		if (shop != null) {
			shopModel = modelMapper.map(shop, ShopModel.class);
			shopModel.setAddressModel(modelMapper.map(shop.getAddress(), AddressModel.class));
		}

		return shopModel;
	}

}
