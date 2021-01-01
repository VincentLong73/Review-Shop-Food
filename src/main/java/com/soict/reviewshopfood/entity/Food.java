package com.soict.reviewshopfood.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="food")
public class Food implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="content")
	private String content;
	
	@Column(name="short_description")
	private String shortDescription;
	
	@Column(name="price")
	private float price;
	
	@Column(name="view")
	private int view;
	
	@Column(name="rate")
	private int rate;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="is_delete")
	private boolean isDelete;
	
	
	
	
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL )
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<ImageFood> imageFoods = new ArrayList<>();

	@OneToOne
	@JoinColumn(name="shop_id")
	private Shop shop;
	
	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL )
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<Comment> comments = new ArrayList<>();
	
}
