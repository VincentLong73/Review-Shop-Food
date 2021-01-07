package com.soict.reviewshopfood.model;

import java.util.Date;
import java.util.List;

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
	private String userName;
	private int foodId;
	private Date createdAt;
	private int commentParentId;
	private int countLike;
	private int rate;
	private CommentModel commentParent;
	private List<LikeModel> listLike;
	private String avatar;
}
