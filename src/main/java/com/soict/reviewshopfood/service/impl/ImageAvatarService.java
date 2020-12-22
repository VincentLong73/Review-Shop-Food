package com.soict.reviewshopfood.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.dao.IImageAvatarDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.ImageAvatar;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.model.ImageAvatarModel;
import com.soict.reviewshopfood.service.IImageAvatarService;

@Service
public class ImageAvatarService implements IImageAvatarService{
	@Autowired
	private IImageAvatarDAO imageDao;
	@Autowired
	private IUserDAO userDao;
	@Override
	public void storeFileImageAvatar(MultipartFile file,int userId) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
			}
			ImageAvatar image = new ImageAvatar();
			image.setFileName(fileName);
			image.setFileType(file.getContentType());
			image.setData(file.getBytes());
			image.setUser(userDao.getOne(userId));
			
			imageDao.save(image);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
		
	}
	@Override
	public ImageAvatarModel getImageAvatar(int id) {
		ImageAvatar image = imageDao.getOne(id);
		ImageAvatarModel imageModel = new ImageAvatarModel();
		imageModel.setFileName(image.getFileName());
		imageModel.setFileType(image.getFileType());
		imageModel.setData(image.getData());
		imageModel.setUserId(image.getUser().getId());
		return imageModel;
	}
}
