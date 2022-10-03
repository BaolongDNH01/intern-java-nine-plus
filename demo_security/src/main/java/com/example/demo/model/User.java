package com.example.demo.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fullname;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Date dateOfBirth;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Column(nullable = false)
	private String idCard;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private LocalDateTime createdDate;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Role> roles = new ArrayList<>();

	public User() {
		super();
	}

	public User(String fullname, String username, String password, Date dateOfBirth, Gender gender, String idCard,
			String email, String phone, String address, LocalDateTime createdDate, Collection<Role> roles) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.idCard = idCard;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.createdDate = createdDate;
		this.roles = roles;
	}

}
