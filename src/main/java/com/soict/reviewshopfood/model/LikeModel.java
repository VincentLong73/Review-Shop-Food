package com.soict.reviewshopfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeModel {
	private int id;
	private int userId;
	private String userName;
	private int commentId;

}
