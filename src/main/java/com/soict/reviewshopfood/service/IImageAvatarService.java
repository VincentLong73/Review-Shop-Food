package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IImageAvatarService {
	void storeFileImageAvatar(MultipartFile file, String email) throws SQLException;
	Resource getImageAvatar(int userId) throws SQLException;
	Resource getImageAvatarByName(String imageUrl) throws SQLException;
	Resource getImageAvatarByEmail(String email) throws SQLException;
}
