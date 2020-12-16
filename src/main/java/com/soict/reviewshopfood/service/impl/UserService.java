package com.soict.reviewshopfood.service.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void addUser(UserModel userModel) {
		User user = modelMapper.map(userModel, User.class);
		user.setRole(roleDao.findByCode(user.getRole().getCode()));
		userDao.saveAndFlush(user);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkLogin(User user,Cookie[] cookies) {
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

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(userUpdate.getEmail());
			message.setSubject("Change Password");
			message.setText("Hello, We are Aims!\n Your new password is : " + passRandom);
			// Send Message!
			this.emailSender.send(message);
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
		userReturn.setEmail(userTmp.getEmail());
		userReturn.setFullName(userTmp.getFullName());
		userReturn.setCodeRole(userTmp.getRole().getCode());;
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
