package com.soict.reviewshopfood.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodModel {
	private int id;
	private String name;
	private List<String> listImageFoodUrl;
	private List<Integer> markRates;
	private  float rating;
	private String content;
	private String shortDescription;
	private String thumbnail;
	private float price;
	private int view;
	private Date createdAt;
	private String createdBy;
	private Date updateAt;
	private int shopId;
	private MultipartFile thumbnailFile;
	private boolean isDelete;
	private String imageShop;
	private String emailShop;
	private String nameShop;
}
