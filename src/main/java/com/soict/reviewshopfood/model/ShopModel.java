package com.soict.reviewshopfood.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopModel {
	private int id;
	private String nameShop;
	private String description;
	private String image;
	private int userId;
	private Date createdAt;
	private String createdBy;
	private Date updateDate;
	private AddressModel addressModel;
	private boolean isDelete;
}
