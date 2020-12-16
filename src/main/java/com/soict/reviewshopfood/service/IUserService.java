package com.soict.reviewshopfood.service;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;

import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.UserModel;

public interface IUserService {

	public void addUser(UserModel user)	throws SQLException;						//dang ki thanh vien
	public List<UserModel> getListUserByRoleId(String roleCode)	throws SQLException;//lay danh sach user theo role
	public User findByEmail(String email)	throws SQLException;					//Tim user theo email
	UserModel findByEmailAfterLogin(String email) throws SQLException;	
	public boolean checkLogin(User user,Cookie[] cookies)	throws SQLException;					//Kiem tra email va password
	void applyNewPassword(User user)	throws SQLException;						//Cap nhat lai password
	UserModel getUserById(int id)	throws SQLException;							//Lay user theo id
	void blockUser(int id) throws SQLException;									//Admin khoa tai khoan nguoi dung
}
