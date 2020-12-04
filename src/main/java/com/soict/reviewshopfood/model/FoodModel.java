package com.soict.reviewshopfood.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodModel {

	private int id;

	private String name;

	private String image;

	private String content;

	private float price;

	private Date createdAt;

	private String createdBy;

	private int shopId;
}
