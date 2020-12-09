package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Shop;

@Transactional
@Repository
public interface IShopDAO extends JpaRepository<Shop, Integer> {
	
	List<Shop> findShopByNameShopContaining(String nameShop);

}
