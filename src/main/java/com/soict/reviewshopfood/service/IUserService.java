package com.soict.reviewshopfood.service;


import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.UserModel;

public interface IUserService {

	public void addUser(UserModel userModel,String type);
	public User findByUserName(String username);
	public boolean checkLogin(User user);
	void applyNewPassword(User user);
}
