package com.soict.reviewshopfood.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="email")
	private String email;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="update_at")
	private Date updateAt;

	@Column(name="created_by")
	private String createdBy;
	@Column(name="is_delete")
	private boolean isDelete;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<Comment> comments = new ArrayList<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Shop shop = new Shop();
	
	@ManyToOne
	@JoinColumn(name="role_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Role role;
	
	public List<GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(this.role.getCode()));

		return authorities;
	}

	
}
