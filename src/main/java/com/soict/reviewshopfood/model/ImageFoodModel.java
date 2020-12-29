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
	private int foodId;
    private long size;
	public ImageFoodModel(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
	}
}
