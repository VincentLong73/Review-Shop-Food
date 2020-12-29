package com.soict.reviewshopfood.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IAddressDAO;
import com.soict.reviewshopfood.dao.IShopDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.Address;
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
	private IUserDAO userDao;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ShopModel> findShopByNameShop(String nameShop) {

		List<Shop> shops = shopDao.findShopByNameShopContaining(nameShop);
		List<ShopModel> shopModels = new ArrayList<>();
		if (shops != null) {
			for(Shop shop : shops) {
				ShopModel shopModel = new ShopModel();
				shopModel.setUserId(shop.getUser().getId());
				shopModel.setDescription(shop.getDescription());
				shopModel = modelMapper.map(shop, ShopModel.class);
				shopModel.setAddressModel(modelMapper.map(shop.getAddress(), AddressModel.class));
				shopModels.add(shopModel);
			}
		}
		return shopModels;
	}

	@Override
	public void addShop(ShopModel shopModel) {
		Shop shop = modelMapper.map(shopModel, Shop.class);
		shop.setUser(userDao.getOne(shopModel.getUserId()));
		shop.setDescription(shopModel.getDescription());
		shop.setDelete(false);
		addressService.addAddress(shopModel.getAddressModel());
		AddressModel addressModel = shopModel.getAddressModel();
		addressModel.setDelete(false);
		shop.setAddress(addressDao.findByDistrictAndVillageAndStreet(addressModel.getDistrict(), addressModel.getVillage(),addressModel.getStreet()));
		shopDao.save(shop);
	}

	@Override
	public void editShop(ShopModel shopModel) {
		if(shopDao.existsById(shopModel.getId())) {
			Shop shop = shopDao.getOne(shopModel.getId());
			shop = modelMapper.map(shopModel, Shop.class);
			shop.setUpdateDate(new Date());
			addressService.editAddress(shopModel.getAddressModel());
			shop.setAddress(addressDao.getOne(shopModel.getAddressModel().getId()));
			shop.setUser(userDao.getOne(shopModel.getUserId()));
			shopDao.saveAndFlush(shop);
		}
	}

	@Override
	public void deleteShop(int id) {

		if (shopDao.existsById(id)) {
			int addressId = shopDao.getOne(id).getAddress().getId();
			Shop shop = shopDao.getOne(id);
			shop.setDelete(true);
			shopDao.saveAndFlush(shop);
			Address address = addressDao.getOne(addressId);
			address.setDelete(true);
			addressDao.saveAndFlush(address);
		}

	}

	@Override
	public ShopModel findShopById(int id) {
		
		Shop shop = shopDao.getOne(id);
		ShopModel shopModel = new ShopModel();
		if (shop != null) {
			shopModel = modelMapper.map(shop, ShopModel.class);
			shopModel.setUserId(shop.getUser().getId());
			shopModel.setAddressModel(modelMapper.map(shop.getAddress(), AddressModel.class));
		}
		return shopModel;
	}

	@Override
	public List<ShopModel> getListShop() {
		List<Shop> shops = shopDao.findAll();
		List<ShopModel> shopModels = new ArrayList<ShopModel>();
		for(Shop shop : shops) {
			ShopModel shopModel = new ShopModel();
			shopModel.setUserId(shop.getUser().getId());
			shopModel = modelMapper.map(shop, ShopModel.class);
			shopModels.add(shopModel);
		}
		return shopModels;
	}

}
