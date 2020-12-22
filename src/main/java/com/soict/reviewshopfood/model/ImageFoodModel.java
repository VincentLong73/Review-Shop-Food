package com.soict.reviewshopfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFoodModel {
	private int id;
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private byte[] data;
	private int foodId;
	public ImageFoodModel(String fileName, String fileDownloadUri, String contentType, long size) {
		// TODO Auto-generated constructor stub
	}
}
