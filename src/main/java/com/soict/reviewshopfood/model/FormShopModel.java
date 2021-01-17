package com.soict.reviewshopfood.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormShopModel {
	private int id;
	private String fullName;
	private String nameShop;
	private String phone;
	private String email;
	private boolean active;
	private String country;
	private String province;
	private String district;
	private String village;
	private String street;
	private Date createdAt;
	private String codeRole;
	private String imageUrl;
	private String description;
	private String createdBy;
}
