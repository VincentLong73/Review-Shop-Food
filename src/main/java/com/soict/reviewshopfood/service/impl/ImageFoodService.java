package com.soict.reviewshopfood.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.IImageFoodDAO;
import com.soict.reviewshopfood.entity.ImageFood;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.model.ImageFoodModel;
import com.soict.reviewshopfood.service.IImageFoodService;

@Service
public class ImageFoodService implements IImageFoodService{
	@Autowired
	private IImageFoodDAO imageFoodDao;
	@Autowired
	private IFoodDAO foodDao;
	@Override
	public void storeFileImageFood(MultipartFile[] files,int foodId) {
		
		for(MultipartFile file : files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			try {
				if (fileName.contains("..")) {
					throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
				}
				ImageFood image = new ImageFood();
				image.setFileName(fileName);
				image.setFileType(file.getContentType());
				image.setData(file.getBytes());
				image.setFood(foodDao.getOne(foodId));
				imageFoodDao.save(image);
			} catch (IOException e) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
			}
		}
	}

	@Override
	public List<ImageFoodModel> getImageFood(int foodId) {
		List<ImageFoodModel> listImageFoodModel = new ArrayList<ImageFoodModel>();
		List<ImageFood> listImageFood = imageFoodDao.findByFoodId(foodId);
		for(ImageFood imageFood : listImageFood) {
			ImageFoodModel imageFoodModel = new ImageFoodModel();
			imageFoodModel.setFileName(imageFood.getFileName());
			imageFoodModel.setFileType(imageFood.getFileType());
			imageFoodModel.setId(imageFood.getId());
			imageFoodModel.setData(imageFood.getData());
			imageFoodModel.setFoodId(imageFood.getFood().getId());
			listImageFoodModel.add(imageFoodModel);
		}
		
		return listImageFoodModel;
	}

}
