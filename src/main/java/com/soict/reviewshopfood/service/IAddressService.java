package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import com.soict.reviewshopfood.model.AddressModel;

public interface IAddressService {
	
	void addAddress(AddressModel addressModel) throws SQLException;		//them dia chi ( dia chi theo shop)
	void editAddress(AddressModel addressModel) throws SQLException;	//sua dia chi
//	AddressModel findByDistrict(String district) throws SQLException;	
//	AddressModel findByVillage(String village) throws SQLException;
//	AddressModel findByStreet(String street) throws SQLException;
}
