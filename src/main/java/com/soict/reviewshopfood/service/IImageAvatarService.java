package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import org.springframework.core.io.Resource;


public interface IImageAvatarService {
	
	Resource getImageAvatar(int userId) throws SQLException;
}
