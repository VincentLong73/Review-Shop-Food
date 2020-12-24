package com.soict.reviewshopfood.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address")
public class Address implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="country")
	private String country;
	
	@Column(name="district")
	private String district;
	
	@Column(name="province")
	private String province;
	
	@Column(name="village")
	private String village;
	
	@Column(name="street")
	private String street;
	
	@Column(name="is_delete")
	private boolean isDelete;

}
