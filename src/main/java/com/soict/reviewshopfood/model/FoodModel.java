package com.soict.reviewshopfood.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodModel {
	private int id;
	private String name;
	private List<Integer> imageFoodIds;
	private List<Integer> markRates;
	private double rating;
	private String content;
	private float price;
	private int view;
	private Date createdAt;
	private String createdBy;
	private int shopId;
	private boolean isDelete;
}
