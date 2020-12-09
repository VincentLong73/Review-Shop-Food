package com.soict.reviewshopfood.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingOfFood{
	private List<Integer> listRate;
	private int foodId;
	private double rating;
}
