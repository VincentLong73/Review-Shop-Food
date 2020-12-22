package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.model.ImageAvatarModel;

public interface IImageAvatarService {
	void storeFileImageAvatar(MultipartFile file,int userId) throws SQLException;
	ImageAvatarModel getImageAvatar(int userId) throws SQLException;
}
