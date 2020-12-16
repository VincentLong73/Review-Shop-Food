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
	private String image;
	private Date createdAt;
	private Date modifiedAt;
	private String createdBy;
	private String modifiedBy;
	private AddressModel addressModel;
	private boolean active;
}
