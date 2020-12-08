package com.soict.reviewshopfood.service;


import java.util.List;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.UserModel;

public interface IUserService {

	public void addUser(UserModel user);
	public List<UserModel> getListUserByRoleId(String roleCode);
	public User findByUserName(String username);
	public boolean checkLogin(User user);
	void applyNewPassword(User user);
}
