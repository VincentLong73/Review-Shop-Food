package com.soict.reviewshopfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageAvatarModel {
	private int id;
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private int userId;
	public ImageAvatarModel(String fileName, String fileDownloadUri, String contentType, long size) {
		// TODO Auto-generated constructor stub
	}
}
