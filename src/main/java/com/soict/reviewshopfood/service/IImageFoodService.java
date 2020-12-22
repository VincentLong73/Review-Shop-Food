package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.model.ImageFoodModel;

public interface IImageFoodService {
	void storeFileImageFood(MultipartFile[] files,int foodId) throws SQLException;
	List<ImageFoodModel> getImageFood(int foodId) throws SQLException;
}
