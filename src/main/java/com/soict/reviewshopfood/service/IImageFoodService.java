package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IImageFoodService {
	void storeFileImageFood(MultipartFile[] files,int foodId) throws SQLException;			//luu anh food
	Resource getImageFood(int imageId) throws SQLException;								// lay anh food bang id
	Resource getImageFoodByName(String imageUrl) throws SQLException;					//lay anh food bang fileName
	Resource getThumbnailFood(String thumbnail) throws SQLException;					//lay anh food bang thumbnail
	List<String> getListImageFood(int foodId) throws SQLException;					//lay list imageId
}
