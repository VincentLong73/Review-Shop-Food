package com.soict.reviewshopfood.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soict.reviewshopfood.entity.Shop;

public class ShopDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	public List<Shop> findShopByNameShop(String nameShop) {
		
		return entityManager.createQuery("select shop from Shop shop where shop.nameShop like = ?1 ", Shop.class)
				.setParameter(1, nameShop)
				.getResultList();
	}
}
