package com.soict.reviewshopfood.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.soict.reviewshopfood.entity.Shop;
import com.soict.reviewshopfood.model.FormNewFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.soict.reviewshopfood.dao.ICommentDAO;
import com.soict.reviewshopfood.dao.IFoodDAO;
import com.soict.reviewshopfood.dao.IImageFoodDAO;
import com.soict.reviewshopfood.dao.IShopDAO;
import com.soict.reviewshopfood.entity.Comment;
import com.soict.reviewshopfood.entity.Food;
import com.soict.reviewshopfood.entity.ImageFood;
import com.soict.reviewshopfood.exception.FileStorageException;
import com.soict.reviewshopfood.model.FoodModel;
import com.soict.reviewshopfood.properties.FileStorageProperties;
import com.soict.reviewshopfood.service.IFoodService;

@Service
public class FoodService implements IFoodService {

	@Autowired
	private IFoodDAO foodDao;
	@Autowired
	private IShopDAO shopDao;
	@Autowired
	private IImageFoodDAO imageFoodDao;
	@Autowired
	private ICommentDAO commentDao;

	private final Path fileStorageLocation;
	final String startFileNameThumbnail = "thumbnail";
	final String startFileNameImageFood = "imageFood";;

	@Autowired
	public FoodService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	//Them mon an ( them ca thumbnail)
	@Override
	public boolean addFood(FoodModel foodModel) {
		if (foodModel != null) {
			Food food = new Food();

			food.setName(foodModel.getName());
			food.setContent(foodModel.getContent());
			food.setShortDescription(foodModel.getShortDescription());
			food.setPrice(foodModel.getPrice());
			food.setView(0);
			food.setCreatedAt(new Date());
			food.setDelete(false);
			food.setCreatedBy(foodModel.getCreatedBy());
			food.setShop(shopDao.getOne(foodModel.getShopId()));

			if (foodModel.getThumbnailFile() != null) {

				food.setThumbnail(createThumbnailUrl(foodModel.getThumbnailFile()));
			} else {
				return false;
			}
			foodDao.save(food);
			return true;
		}
		return false;
	}

	//Sua thong tin mon an ( suan ca thumbnail neu can)
	@Override
	public void editFood(FoodModel foodModel) {
		if (foodDao.existsById(foodModel.getId())) {
			Food food = foodDao.getOne(foodModel.getId());
			if (!food.getName().equals(foodModel.getName()) && foodModel.getName() != null) {
				food.setName(foodModel.getName());
			}
			if (!food.getContent().equals(foodModel.getContent()) && foodModel.getContent() != null) {
				food.setContent(foodModel.getContent());
			}
			if (!(foodModel.getShortDescription() != null && food.getShortDescription().equals(foodModel.getShortDescription()))) {
				food.setShortDescription(foodModel.getShortDescription());
			}
			if (food.getPrice() != (foodModel.getPrice()) && foodModel.getPrice() != 0) {
				food.setPrice(foodModel.getPrice());
			}
			if (foodModel.getThumbnailFile() != null) {

				food.setThumbnail(createThumbnailUrl(foodModel.getThumbnailFile()));
			}
			food.setUpdateAt(new Date());
			foodDao.saveAndFlush(food);
		}
	}

	//Xoa mon an theo id
	@Override
	public void deleteFood(int id) {
		if (foodDao.existsById(id)) {
			Food food = foodDao.getOne(id);
			food.setDelete(true);
			foodDao.saveAndFlush(food);
		}
	}

	//Lay cac mon an theo shop
	@Override
	public List<FoodModel> getFoodByShopId(int shopId) {
		if (shopId != 0) {
			List<Food> foods = foodDao.getFoodByShopId(shopId);
			List<FoodModel> foodModels = getListFoodModel(foods);
			return foodModels;
		} else {
			return null;
		}
	}

	//Lay mon an theo ten khi tim kiem
	@Override
	public List<FoodModel> getListFoodByNameFood(String nameFood) {
		if (nameFood != null) {
			List<Food> foods = foodDao.getListFoodByNameContaining(nameFood);
			List<FoodModel> foodModels = getListFoodModel(foods);
			return foodModels;
		} else {
			return null;
		}
	}

	//Lay tat ca mon an
	@Override
	public List<FoodModel> getListFood() {
		List<Food> foods = foodDao.findAll();
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	//Lay mon an theo shop va con ban
	@Override
	public List<FoodModel> getFoodByShopIdAndActive(int shopId, boolean active) {
		if (shopId != 0) {
			List<Food> foods = foodDao.getFoodByShopIdAndIsDelete(shopId, active);
			List<FoodModel> foodModels = getListFoodModel(foods);
			return foodModels;
		} else {
			return null;
		}
	}

	//lay mon an theo ten va con ban
	@Override
	public List<FoodModel> getListFoodByNameContainingAndActive(String nameFood, boolean active) throws SQLException {
		if (nameFood != null) {
			List<Food> foods = foodDao.getListFoodByNameContainingAndIsDelete(nameFood, active);
			List<FoodModel> foodModels = getListFoodModel(foods);
			return foodModels;
		} else {
			return null;
		}
	}

	//Lay mon an theo luot view nhieu nhat
	@Override
	public List<FoodModel> getFoodByView() throws SQLException {
		List<Food> foods = foodDao.getFoodByOrderByViewDesc();
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	//Ham lay mon an
	private List<FoodModel> getListFoodModel(List<Food> foods) {
		List<FoodModel> foodModels = new ArrayList<>();
		for (Food food : foods) {
			FoodModel foodModel = new FoodModel();

			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setDelete(food.isDelete());
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setView(food.getView());
			foodModel.setShopId(food.getShop().getId());
			foodModel.setUpdateAt(food.getUpdateAt());

			String thumbnail = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/food/foodImage/" + food.getThumbnail()).toUriString();
			foodModel.setThumbnail(thumbnail);

			foodModel.setListImageFoodUrl(getListImageFoodUrl(food.getId()));

			int sumRate = 0;
			List<Comment> listComment = commentDao.getListCommentByFoodId(food.getId());
			for (Comment comment : listComment) {
				sumRate = sumRate + comment.getRate();
			}
			// Tinh rating tu cac diem rate cua food
			foodModel.setRating(food.getRate());

			foodModels.add(foodModel);
		}
		return foodModels;
	}

	//Ham lay url cua anh mon an
	private List<String> getListImageFoodUrl(int foodId) {
		List<String> listImageFoodUrl = new ArrayList<String>();
		List<ImageFood> listImageFood = imageFoodDao.findByFoodId(foodId);
		for (ImageFood image : listImageFood) {
			String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/food/foodImage/" + image.getImageUrl()).toUriString();
			listImageFoodUrl.add(imageUrl);
		}
		return listImageFoodUrl;
	}

//	private List<Integer> getListImageFood(int foodId){
//		List<ImageFoodModel> listImageFood = imageFoodService.getImageFood(foodId);
//		List<Integer> listImageFoodId = new ArrayList<Integer>();
//		for(ImageFoodModel image : listImageFood) {
//			listImageFoodId.add(image.getId());
//		}
//		return listImageFoodId;
//	}

	//Lay mon an theo Id mon an va con ban
	@Override
	public FoodModel getFoodByIdAndActive(int id) throws SQLException {

		if (foodDao.getFoodByIdAndIsDelete(id, false) != null) {
			Food food = foodDao.getFoodByIdAndIsDelete(id, false);
			FoodModel foodModel = new FoodModel();

			foodModel.setId(food.getId());
			foodModel.setName(food.getName());
			foodModel.setContent(food.getContent());
			foodModel.setPrice(food.getPrice());
			foodModel.setCreatedAt(food.getCreatedAt());
			foodModel.setDelete(food.isDelete());
			foodModel.setCreatedBy(food.getCreatedBy());
			foodModel.setUpdateAt(food.getUpdateAt());
			foodModel.setView(food.getView());
			foodModel.setShortDescription(food.getShortDescription());
			foodModel.setShopId(food.getShop().getId());
			String thumbnail = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/food/foodImage/" + food.getThumbnail()).toUriString();
			foodModel.setThumbnail(thumbnail);
			foodModel.setListImageFoodUrl(getListImageFoodUrl(food.getId()));
			int sumRate = 0;
			List<Comment> listComment = commentDao.getListCommentByFoodId(food.getId());
			for (Comment comment : listComment) {
				sumRate = sumRate + comment.getRate();
			}
			// Tinh rating tu cac diem rate cua food
			foodModel.setRating(food.getRate());
			return foodModel;
		}
		return null;

	}

	//lay mon an theo rating
	@Override
	public List<FoodModel> getListFoodByRate() throws SQLException {
		List<Food> foods = foodDao.getFoodByOrderByRateDesc();
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	//Lay mon an dang gan nhat
	@Override
	public List<FoodModel> getFoodByCreatedAtDesc() throws SQLException {
		List<Food> foods = foodDao.getFoodByCreatedAtDesc();
		List<FoodModel> foodModels = getListFoodModel(foods);
		return foodModels;
	}

	//tao url cho thumbnail cua mon an
	private String createThumbnailUrl(MultipartFile thumbnailFile) {
		String fileName = StringUtils.cleanPath(thumbnailFile.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String token = String.valueOf(timestamp.getTime());
			fileName = startFileNameThumbnail + token.concat(fileName);

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(thumbnailFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
		return fileName;
	}
	public Food getFoodById(int id){
		return foodDao.getFoodById(id);
	}
	public Food saveNewFood(MultipartFile thumbnail, MultipartFile[] foodImages, FormNewFood formNewFood, Shop shop) throws IOException {
		Food food = new Food();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String token = String.valueOf(timestamp.getTime());
		String fileName = token.concat(Objects.requireNonNull(thumbnail.getOriginalFilename()));
		Path path = Paths.get("uploads/foods/");
		InputStream inputStream = thumbnail.getInputStream();
		Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		food.setThumbnail(fileName);
		food.setContent(formNewFood.getContent());
		food.setName(formNewFood.getName());
		food.setShortDescription(formNewFood.getShortDescription());
		food.setPrice(formNewFood.getPrice());
		food.setShop(shop);
		food = foodDao.save(food);

		for(int i = 0; i<foodImages.length; i++){
			ImageFood imageFood = new ImageFood();
			token = String.valueOf(timestamp.getTime());
			fileName = token.concat(Objects.requireNonNull(foodImages[i].getOriginalFilename()));
			inputStream = foodImages[i].getInputStream();
			Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			imageFood.setImageUrl(fileName);
			imageFood.setFood(food);
			imageFoodDao.save(imageFood);
		}
		return food;
	}

}
