package com.soict.reviewshopfood.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.User;

@Transactional
@Repository
public interface IUserDAO extends JpaRepository<User, Integer>{

	User findByEmail(String email);
	List<User> findByRoleId(int roleId);
	@Query(nativeQuery = true, value="select * from user where active = 0 AND is_delete = 0 AND role_id = 2 AND created_at < :date ORDER BY created_at DESC limit 10")
	List<User> getUserByDate(Date date);
	@Query(nativeQuery = true, value="select * from user where role_id = 2 ORDER BY created_at DESC")
	List<User> getShopActive();
	@Query(nativeQuery = true, value="select * from user where role_id != 2 ORDER BY created_at DESC")
	List<User> getUser();
	@Query(nativeQuery = true, value="select COUNT(id) from user where role_id = 3")
	long getTotalCustomer();
	@Query(nativeQuery = true, value="select COUNT(id) from user where role_id = 2")
	long getTotalShop();
}
