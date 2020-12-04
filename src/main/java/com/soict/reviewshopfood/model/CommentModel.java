package com.soict.reviewshopfood.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {

	private int id;

	private String content;

	private int userId;

	private int foodId;

	private Date createdAt;

	private int commentParentId;
}
