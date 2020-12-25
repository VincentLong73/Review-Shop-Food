package com.soict.reviewshopfood.service;

import java.sql.SQLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IImageAvatarService {
	void storeFileImageAvatar(MultipartFile file,int userId) throws SQLException;
	Resource getImageAvatar(int userId) throws SQLException;
}
