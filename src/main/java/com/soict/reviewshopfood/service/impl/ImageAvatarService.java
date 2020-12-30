package com.soict.reviewshopfood.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.User;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.exception.MyFileNotFoundException;
import com.soict.reviewshopfood.properties.FileStorageProperties;
import com.soict.reviewshopfood.service.IImageAvatarService;

@Service
public class ImageAvatarService implements IImageAvatarService {

	@Autowired
	private IUserDAO userDao;

	private final Path fileStorageLocation;

	@Autowired
	public ImageAvatarService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	@Override
	public void storeFileImageAvatar(MultipartFile file, String email) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
		
		User user = userDao.findByEmail(email);
		user.setImageUrl(fileName);
		userDao.saveAndFlush(user);
		
	}

	@Override
	public Resource getImageAvatar(int userId) throws SQLException {
		try {
			Path filePath = this.fileStorageLocation.resolve(userDao.getOne(userId).getImageUrl()).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found avatar !");
			}

		} catch (MalformedURLException e) {
			throw new MyFileNotFoundException("File not found avatar !");
		}
	}
	
	@Override
	public Resource getImageAvatarByEmail(String email) throws SQLException {
		try {
			Path filePath = this.fileStorageLocation.resolve(userDao.findByEmail(email).getImageUrl()).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found avatar !");
			}

		} catch (MalformedURLException e) {
			throw new MyFileNotFoundException("File not found avatar !");
		}
	}

	@Override
	public Resource getImageAvatarByName(String imageUrl) throws SQLException {
		try {
			Path filePath = this.fileStorageLocation.resolve(imageUrl).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found avatar !");
			}

		} catch (MalformedURLException e) {
			throw new MyFileNotFoundException("File not found avatar !");
		}
	}

}
