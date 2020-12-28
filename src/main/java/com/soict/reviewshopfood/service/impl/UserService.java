package com.soict.reviewshopfood.service.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IRoleDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.IUserService;


@Service
public class UserService implements IUserService, UserDetailsService{
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IRoleDAO roleDao;
	@Override
	public boolean addUser(UserModel userModel) {
		User user = modelMapper.map(userModel, User.class);
		if(userDao.findByEmail(user.getEmail())==null) {
			user.setCreatedAt(new Date());
			user.setActive(true);
			user.setRole(roleDao.findByCode(user.getRole().getCode()));
			userDao.save(user);
			return true;
		}
		return false;
		
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkLogin(User user) {
		User userfind = userDao.findByEmail(user.getEmail());
		if (userfind == null) {
			return false;
		} else {
			if (user.getPassword().equals(userfind.getPassword()) && userfind.isActive()) {
				return true;
			} else {
				return false;
			}
		}
	
	}
	@Override
	public void applyNewPassword(User user) {
		User userUpdate = userDao.findByEmail(user.getEmail());
		String passRandom = RandomStringUtils.randomAlphanumeric(8);
		if (userUpdate != null) {
			userUpdate.setPassword(passRandom);
			userDao.save(userUpdate);
		}
		
	}

	@Override
	public List<UserModel> getListUserByRoleId(String roleCode) {
		List<User> users = userDao.findByRoleId(roleDao.findByCode(roleCode).getId());
		List<UserModel> userModels = new ArrayList<UserModel>();
		for(User user : users) {
			UserModel userModel = new UserModel();
			userModel = modelMapper.map(user, UserModel.class);
			userModels.add(userModel);
		}
		return userModels;
	}

	@Override
	public UserModel getUserById(int id) throws SQLException {
		User user = userDao.getOne(id);
		UserModel userModel = new UserModel();
		if(user!=null) {
			userModel = modelMapper.map(user, UserModel.class);
			userModel.setPassword(null);
		}
		return userModel;
	}

	@Override
	public void blockUser(int id) throws SQLException {
		User user = userDao.getOne(id);
		if(user != null) {
			user.setActive(false);
			userDao.saveAndFlush(user);
		}
		
	}

	@Override
	public UserModel findByEmailAfterLogin(String email) throws SQLException {
		User userTmp = userDao.findByEmail(email);
		UserModel userReturn = new UserModel();
		userReturn.setId(userTmp.getId());
		userReturn.setEmail(userTmp.getEmail());
		userReturn.setFullName(userTmp.getFullName());
		userReturn.setUserName(userTmp.getUserName());
		userReturn.setCodeRole(userTmp.getRole().getCode());
		userReturn.setActive(userTmp.isActive());
		return userReturn;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User " + email + " was not found in the database");
		}
		String role = user.getRole().getCode();
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority(role);
		grantList.add(authority);
		boolean enable = user.isActive();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantList);
		return userDetails;
	}

	

}
