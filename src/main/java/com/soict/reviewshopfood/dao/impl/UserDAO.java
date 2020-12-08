package com.soict.reviewshopfood.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soict.reviewshopfood.entity.User;

public class UserDAO {
	@PersistenceContext
	EntityManager entityManager;

	public List<User> findByIdRole(int idRole) {

		 return entityManager.createQuery("select user from User user where user.role.id = ?1", User.class)
				 .setParameter(1, idRole).getResultList();
	}
}
