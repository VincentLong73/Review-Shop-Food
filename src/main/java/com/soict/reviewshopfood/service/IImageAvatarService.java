package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;


public interface IImageAvatarService {
	void storeFileImageAvatar(MultipartFile file, String email) throws SQLException;
	String getImageAvatar(String email) throws SQLException;
	String getImageAvatar1(String email) throws SQLException;
}
