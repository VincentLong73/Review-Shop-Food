package com.soict.reviewshopfood.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IImageFoodService {
	void storeFileImageFood(MultipartFile[] files,int foodId) throws SQLException;
	Resource getImageFood(int imageId) throws SQLException;
	List<Integer> getListIdImageFood(int foodId) throws SQLException;
}
