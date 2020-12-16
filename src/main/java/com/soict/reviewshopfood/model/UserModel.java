package com.soict.reviewshopfood.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	
	
	private int id;
	private String userName;
	private String password;
	private String fullName;
	private String email;
	private boolean active;
	private Date createdAt;
	private Date modifiedDate;
	private String codeRole;
	
	

}
