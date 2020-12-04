package com.soict.reviewshopfood.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Address;

@Transactional
@Repository
public interface IAddressDAO extends JpaRepository<Address, Integer> {
	
	Address findByDistrict(String district);

	Address findByVillage(String village);

	Address findByStreet(String street);
	
	Address findByDistrictAndVillageAndStreet(String district, String village, String street);

}
