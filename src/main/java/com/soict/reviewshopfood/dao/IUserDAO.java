package com.soict.reviewshopfood.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.User;

@Transactional
@Repository
public interface IUserDAO extends JpaRepository<User, Integer>{

	User findByEmail(String email);
	List<User> findByRoleId(int roleId);
}
