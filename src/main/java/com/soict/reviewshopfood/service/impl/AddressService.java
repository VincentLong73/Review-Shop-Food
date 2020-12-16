package com.soict.reviewshopfood.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IAddressDAO;
import com.soict.reviewshopfood.entity.Address;
import com.soict.reviewshopfood.model.AddressModel;
import com.soict.reviewshopfood.service.IAddressService;

@Service
public class AddressService implements IAddressService {
	
	@Autowired
	private IAddressDAO addressDao;
	
	@Autowired
	private ModelMapper modelMapper;

//	@Override
//	public AddressModel findByDistrict(String district) {
//		
//		return modelMapper.map(addressDao.findByDistrict(district),AddressModel.class);
//	}
//
//	@Override
//	public AddressModel findByVillage(String village) {
//		
//		return modelMapper.map(addressDao.findByVillage(village),AddressModel.class);
//	}
//
//	@Override
//	public AddressModel findByStreet(String street) {
//		
//		return modelMapper.map(addressDao.findByStreet(street),AddressModel.class);
//	}

	@Override
	public void addAddress(AddressModel addressModel) {
		
		addressDao.saveAndFlush(modelMapper.map(addressModel, Address.class));
		
	}

	@Override
	public void editAddress(AddressModel addressModel) {
		
		Address address = addressDao.getOne(addressModel.getId());
		
		address.setCountry(addressModel.getCountry());
		address.setDistrict(addressModel.getDistrict());
		address.setVillage(addressModel.getVillage());
		address.setStreet(addressModel.getStreet());
		
		addressDao.save(address);
		
	}

}
