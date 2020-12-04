package com.soict.reviewshopfood.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soict.reviewshopfood.entity.Role;

@Transactional
@Repository
public interface IRoleDAO extends JpaRepository<Role, Integer> {

	Role findByCode(String code);
}
