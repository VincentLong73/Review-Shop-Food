package com.soict.reviewshopfood.service.impl;

import com.soict.reviewshopfood.dao.IRoleDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.service.IUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IRoleDAO roleDao;

	//	@Autowired
//	private JavaMailSender emailSender;
	@Override
	public boolean addUser(UserModel userModel) {
		User user = modelMapper.map(userModel, User.class);
		if (userDao.findByEmail(user.getEmail()) == null) {
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
		if (email != null) {
			return userDao.findByEmail(email);
		}
		return null;
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
//	@Override
//	public void applyNewPassword(User user) {
//		User userUpdate = userDao.findByEmail(user.getEmail());
//		String passRandom = RandomStringUtils.randomAlphanumeric(8);
//		if (userUpdate != null) {
//			userUpdate.setPassword(passRandom);
//			userDao.save(userUpdate);
//
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setTo(userUpdate.getEmail());
//			message.setSubject("Change Password");
//			message.setText("Hello, We are Aims!\n Your new password is : " + passRandom);
//			// Send Message!
//			this.emailSender.send(message);
//		}
//		
//	}

	@Override
	public List<UserModel> getListUserByRoleId(String roleCode) {
		List<User> users = userDao.findByRoleId(roleDao.findByCode(roleCode).getId());
		List<UserModel> userModels = new ArrayList<UserModel>();
		if (users != null && users.size() > 0) {
			for (User user : users) {
				UserModel userModel = new UserModel();
				userModel = modelMapper.map(user, UserModel.class);
				userModels.add(userModel);
			}
		}
		return userModels;
	}

	@Override
	public UserModel getUserById(int id) throws SQLException {
		UserModel userModel = new UserModel();
		if (userDao.existsById(id)) {
			User user = userDao.getOne(id);
			userModel = modelMapper.map(user, UserModel.class);
			userModel.setPassword(null);
		}
		return userModel;
	}

	@Override
	public void blockUser(int id) throws SQLException {
		if (userDao.existsById(id)) {
			User user = userDao.getOne(id);
			user.setActive(false);
			userDao.saveAndFlush(user);
		}
	}

	@Override
	public UserModel getUserByEmail(String email) {
		UserModel userModel = new UserModel();
		User user = userDao.findByEmail(email);
		userModel = modelMapper.map(user, UserModel.class);
		userModel.setPassword(null);
		return userModel;
	}


	@Override
	public UserModel findByEmailAfterLogin(String email) throws SQLException {
		if (email != null) {
			if (userDao.findByEmail(email) != null) {
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
		}
		return null;
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

		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), enable, accountNonExpired, credentialsNonExpired, accountNonLocked, grantList);
		return userDetails;
	}

	@Override
	public void updateUser(User user) {
		User userfind = userDao.findByEmail(user.getEmail());
		userfind.setFullName(user.getFullName());
		userfind.setUserName(user.getUserName());
		userfind.setPassword(user.getPassword());
		userDao.save(userfind);
	}

}
