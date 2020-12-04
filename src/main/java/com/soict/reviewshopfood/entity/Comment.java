package com.soict.reviewshopfood.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="comment")
public class Comment implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="created_at")
	private Date createdAt;


	@ManyToOne
	@JoinColumn(name="user_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private User user;
	
	@ManyToOne
	@JoinColumn(name="food_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Food food;
	
	@OneToOne
	@JoinColumn(name="parent_comment_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Comment comment;
}
