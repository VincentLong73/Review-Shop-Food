package com.soict.reviewshopfood.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.dao.IImageAvatarDAO;
import com.soict.reviewshopfood.dao.IUserDAO;
import com.soict.reviewshopfood.entity.ImageAvatar;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.exception.MyFileNotFoundException;
import com.soict.reviewshopfood.properties.FileStorageProperties;
import com.soict.reviewshopfood.service.IImageAvatarService;

@Service
public class ImageAvatarService implements IImageAvatarService {
	@Autowired
	private IImageAvatarDAO imageDao;
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
	public void storeFileImageAvatar(MultipartFile file, int userId) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);
			
			ImageAvatar image = new ImageAvatar();
			image.setFileName(fileName);
			image.setFileType(file.getContentType());
			image.setUser(userDao.getOne(userId));

			imageDao.save(image);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}

	}

	@Override
	public Resource getImageAvatar(int userId) {
		try {
			ImageAvatar image = imageDao.findByUserId(userId);
			Path filePath = this.fileStorageLocation.resolve(image.getFileName()).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}else {
				throw new MyFileNotFoundException("File not found avatar !");
			}
			
		}catch(MalformedURLException e) {
			throw new MyFileNotFoundException("File not found avatar !");
		}
		
	}
}
