package com.soict.reviewshopfood.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IRoleDAO;
import com.soict.reviewshopfood.entity.Role;
import com.soict.reviewshopfood.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDAO roleDao;
	@Override
	public Role getRoleById(int id) {
		
		return roleDao.getOne(id);
	}

}
