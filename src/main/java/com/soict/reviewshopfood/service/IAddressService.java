package com.soict.reviewshopfood.service;

import com.soict.reviewshopfood.model.AddressModel;

public interface IAddressService {
	
	void addAddress(AddressModel addressModel);
	
	void editAddress(AddressModel addressModel);

	AddressModel findByDistrict(String district);
	
	AddressModel findByVillage(String village);
	
	AddressModel findByStreet(String street);
}
