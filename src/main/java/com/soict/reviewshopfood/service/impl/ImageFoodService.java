package com.soict.reviewshopfood.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.IImageFoodDAO;
import com.soict.reviewshopfood.entity.ImageFood;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.exception.MyFileNotFoundException;
import com.soict.reviewshopfood.properties.FileStorageProperties;
import com.soict.reviewshopfood.service.IImageFoodService;

@Service
public class ImageFoodService implements IImageFoodService{
	@Autowired
	private IImageFoodDAO imageFoodDao;
	@Autowired
	private IFoodDAO foodDao;
	
	private final Path fileStorageLocation;

    @Autowired
    public ImageFoodService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
	@Override
	public void storeFileImageFood(MultipartFile[] files,int foodId) {
		
		for(MultipartFile file : files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			try {
				if (fileName.contains("..")) {
					throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
				}
				Path targetLocation = this.fileStorageLocation.resolve(fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				ImageFood image = new ImageFood();
				image.setImageUrl(fileName);
				image.setFood(foodDao.getOne(foodId));
				imageFoodDao.save(image);
			} catch (IOException e) {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
			}
		}
	}

	@Override
	public Resource getImageFood(int imageId) {
		try {
			ImageFood image = imageFoodDao.getOne(imageId);
			Path filePath = this.fileStorageLocation.resolve(image.getImageUrl()).normalize();
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
	@Override
	public List<Integer> getListIdImageFood(int foodId) throws SQLException {
		List<Integer> listImageFoodId = new ArrayList<Integer>();
		List<ImageFood> listImageFood = imageFoodDao.findByFoodId(foodId);
		for(ImageFood image : listImageFood) {
			listImageFoodId.add(image.getId());
		}
		return listImageFoodId;
	}
	@Override
	public Resource getImageFoodByName(String imageUrl) throws SQLException {
		try {
			Path filePath = this.fileStorageLocation.resolve(imageUrl).normalize();
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
