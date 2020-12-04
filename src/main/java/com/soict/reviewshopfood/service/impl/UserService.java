package com.soict.reviewshopfood.service.impl;


import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.soict.reviewshopfood.dao.IRoleDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IRoleDAO roleDao;
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void addUser(UserModel userModel,String code) {
		
		
		User user = modelMapper.map(userModel, User.class);
		user.setRole(roleDao.findByCode(code));
		
		
		userDao.saveAndFlush(user);
		
	}

	@Override
	public User findByUserName(String username) {
		
		return userDao.findByUserName(username);
	}

	@Override
	public boolean checkLogin(User user) {
		
		User userCheck = userDao.findByUserName(user.getUserName());
		if(userCheck != null) {
			if(userCheck.getPassword().equals(user.getPassword()));
			return true;
		}
		else {
			return false;
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

}
