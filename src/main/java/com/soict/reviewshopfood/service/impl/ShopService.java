package com.soict.reviewshopfood.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.soict.reviewshopfood.entity.Food;
import com.soict.reviewshopfood.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IAddressDAO;
import com.soict.reviewshopfood.dao.IRoleDAO;
import com.soict.reviewshopfood.dao.IShopDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.Address;
import com.soict.reviewshopfood.entity.Shop;
import com.soict.reviewshopfood.entity.User;
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
	private IRoleDAO roleDao;
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
			shop.setUpdateAt(new Date());
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
		ShopModel shopModel = modelMapper.map(shop, ShopModel.class);
		shopModel.setUserId(shop.getUser().getId());
		shopModel.setAddressModel(modelMapper.map(shop.getAddress(), AddressModel.class));
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

	public Shop findShopByUserId(int userId) {
		return shopDao.findShopByUserId(userId);
	}
	
	@Override
	public boolean registerShop(FormShopModel formShopModel) {
		User user = new User();
		Shop shop = new Shop();
		
		if(userDao.findByEmail(formShopModel.getEmail()) != null) {
			return false;
		}
		
		//luu tai khoan chu shop
		
		user.setFullName(formShopModel.getFullName());
		user.setUserName(formShopModel.getFullName());
		user.setEmail(formShopModel.getEmail());
		user.setPassword(RandomStringUtils.randomAlphanumeric(8));
		user.setActive(false);
		//user.setCreatedBy(formShopModel.getCreatedBy());
		user.setRole(roleDao.findByCode("ROLE_SHOP"));
		user.setCreatedAt(new Date());
		user = userDao.save(user);
		
		//luu thong tin shop
		shop.setNameShop(formShopModel.getNameShop());
		shop.setUser(user);
		shop.setDescription(formShopModel.getDescription());
		shop.setCreatedAt(new Date());
		shop.setDelete(false);
		shop.setPhone(formShopModel.getPhone());
		
		//Luu vi tri
		AddressModel addressModel =new AddressModel();
		
		addressModel.setCountry(formShopModel.getCountry());
		addressModel.setProvince(formShopModel.getProvince());
		addressModel.setDistrict(formShopModel.getDistrict());
		addressModel.setVillage(formShopModel.getVillage());
		addressModel.setStreet(formShopModel.getStreet());
		addressModel.setDelete(true);
		
		Address address = addressDao.save(modelMapper.map(addressModel, Address.class));
		
		shop.setAddress(address);
		shopDao.save(shop);
		
		return true;
	}

	@Override
	public boolean activeShop(int shopId) {
		
		if(!shopDao.existsById(shopId)) {
			return false;
		}
		Shop shop = shopDao.getOne(shopId);
		shop.setDelete(false);
		shopDao.saveAndFlush(shop);
		if(!userDao.existsById(shop.getUser().getId())) {
			return false;
		}
		User user = userDao.getOne(shop.getUser().getId());
		user.setActive(true);
		userDao.saveAndFlush(user);
		if(!addressDao.existsById(shop.getAddress().getId())) {
			return false;
		}
		Address address = addressDao.getOne(shop.getAddress().getId());
		address.setDelete(false);
		addressDao.saveAndFlush(address);
		
		return true;
	}
	public Shop editShop(FormEditShop formEditShop, String email){
		User user = userDao.findByEmail(email);
		Shop shop = this.findShopByUserId(user.getShop().getId());
		Address address = addressDao.getOne(shop.getAddress().getId());
		address.setDistrict(formEditShop.getDistrict());
		address.setProvince(formEditShop.getProvince());
		address.setStreet(formEditShop.getStreet());
		address.setVillage(formEditShop.getVillage());
		addressDao.save(address);
		shop.setPhone(formEditShop.getPhone());
		shop.setDescription(formEditShop.getDescription());
		shop.setAddress(address);
		shopDao.save(shop);
		return shop;
	}
}
