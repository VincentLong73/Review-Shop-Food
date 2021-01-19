package com.soict.reviewshopfood.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.soict.reviewshopfood.dao.IAddressDAO;
import com.soict.reviewshopfood.dao.IRoleDAO;
import com.soict.reviewshopfood.dao.IShopDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.Address;
import com.soict.reviewshopfood.entity.Shop;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.model.AddressModel;
import com.soict.reviewshopfood.model.FormShopModel;
import com.soict.reviewshopfood.model.UserModel;
import com.soict.reviewshopfood.properties.FileStorageProperties;
import com.soict.reviewshopfood.service.IUserService;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IRoleDAO roleDao;
	@Autowired
	private IAddressDAO addressDao;
	@Autowired
	private IShopDAO shopDao;
	
	@Autowired
	private JavaMailSender emailSender;

	private final Path fileStorageLocation;

	@Autowired
	public UserService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	@Override
	public User addUser(UserModel userModel) {
		User user = modelMapper.map(userModel, User.class);
		if (userDao.findByEmail(user.getEmail()) == null) {
			user.setCreatedAt(new Date());
			user.setActive(true);
			user.setRole(roleDao.findByCode(user.getRole().getCode()));
			return userDao.save(user);

		}
		return null;
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
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(userUpdate.getEmail());
			message.setSubject("Change Password");
			message.setText("Hello, We are Admin!\n Your new password is : " + passRandom);
			// Send Message!
			this.emailSender.send(message);
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
	public List<UserModel> getListUser() {
		List<User> users = userDao.getUser();
		List<UserModel> userModels = new ArrayList<UserModel>();
		if (users != null && users.size() > 0) {
			for (User user : users) {
				UserModel userModel = new UserModel();
				userModel = modelMapper.map(user, UserModel.class);
				userModel.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/user/avatar/" + user.getImageUrl()).toUriString());
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
			userModel.setImageUrl(user.getImageUrl());
			userModel.setPassword(null);
		}
		return userModel;
	}

	public void blockOrUnblockUser(int id, boolean active) throws SQLException {
		if(userDao.existsById(id)) {
			User user = userDao.getOne(id);
			user.setActive(active);
			userDao.saveAndFlush(user);
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(user.getEmail());
			if(active) {
				message.setSubject("Active account");
				message.setText("Hello, We are Admin!\n Your account is actived");
			}else {
				message.setSubject("Block account");
				message.setText("Hello, We are Admin!\n Your account is blocked");
			}
			
			// Send Message!
			this.emailSender.send(message);
		}
	}

	@Override
	public UserModel getUserByEmail(String email) {
		UserModel userModel = new UserModel();
		User user = userDao.findByEmail(email);
		userModel = modelMapper.map(user, UserModel.class);
		userModel.setPassword(null);
		userModel.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/user/avatar/" +user.getImageUrl()).toUriString());
		return userModel;
	}

	@Override
	public void updateUser(User user) {
		User userfind = userDao.findByEmail(user.getEmail());
		userfind.setFullName(user.getFullName());
		userfind.setUserName(user.getUserName());
		userfind.setPassword(user.getPassword());
		userDao.save(userfind);
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
	public boolean editUser(UserModel userModel, String email) throws SQLException {
		
		if(userDao.findByEmail(email) != null) {
			User user = userDao.findByEmail(email);
			if (user.getUserName().equals(userModel.getUserName()) && userModel.getUserName() != null) {
				user.setUserName(userModel.getUserName());
			}
			if (user.getFullName().equals(userModel.getFullName()) && userModel.getFullName() != null) {
				user.setFullName(userModel.getFullName());
			}
			user.setUpdateAt(new Date());
			userDao.saveAndFlush(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean registerShop(FormShopModel formShopModel) {
		User user = new User();
		Shop shop = new Shop();

		if (userDao.findByEmail(formShopModel.getEmail()) != null) {
			return false;
		}

		//luu tai khoan chu shop

		user.setFullName(formShopModel.getFullName());
		//user.setUserName(formShopModel.getCreatedBy());
		user.setEmail(formShopModel.getEmail());
		user.setActive(false);
		//user.setCreatedBy(formShopModel.getCreatedBy());
		user.setRole(roleDao.findByCode("ROLE_SHOP"));
		user.setCreatedAt(new Date());
		userDao.save(user);

		//luu thong tin shop

		shop.setUser(userDao.findByEmail(user.getEmail()));
		shop.setDescription(formShopModel.getDescription());
		shop.setDelete(true);

		//Luu vi tri
		AddressModel addressModel = new AddressModel();

		addressModel.setCountry(formShopModel.getCountry());
		addressModel.setProvince(formShopModel.getProvince());
		addressModel.setDistrict(formShopModel.getDistrict());
		addressModel.setVillage(formShopModel.getVillage());
		addressModel.setStreet(formShopModel.getStreet());
		addressModel.setDelete(true);

		addressDao.save(modelMapper.map(addressModel, Address.class));

		shop.setAddress(addressDao.findByDistrictAndVillageAndStreet(addressModel.getDistrict(), addressModel.getVillage(), addressModel.getStreet()));
		shopDao.save(shop);

		return true;
	}

	public List<User> getRequestCreateShop(Date date) {
		return userDao.getUserByDate(date);
	}

	public List<User> getShopActive() {
		return userDao.getShopActive();
	}

	public void deleteUser(int id) throws Exception {
		User user = userDao.getOne(id);
		user.setDelete(true);
		userDao.saveAndFlush(user);
	}

	public void unDeleteUser(int id) throws Exception {
		User user = userDao.getOne(id);
		user.setDelete(false);
		userDao.saveAndFlush(user);
	}
	public void toggleActive(int id) throws Exception {
		User user = userDao.getOne(id);
		user.setActive(!user.isActive());
		userDao.saveAndFlush(user);
	}
	public long getTotalCustomer() throws Exception {
		return userDao.getTotalCustomer();
	}
	public long getTotalShop() throws Exception {
		return userDao.getTotalShop();
	}
}
