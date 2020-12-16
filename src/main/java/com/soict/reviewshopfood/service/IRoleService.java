package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import com.soict.reviewshopfood.entity.Role;

public interface IRoleService {

	public Role getRoleById(int id) throws SQLException; //Lay role
}
