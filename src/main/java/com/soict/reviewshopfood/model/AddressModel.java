package com.soict.reviewshopfood.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

	private int id;

	private String country;

	private String district;

	private String village;

	private String street;

}
