package com.hsn.restaurant.entity;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hsn.restaurant.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bill extends BaseEntity{
	
	private String fullName;
	private String address;
	private String phoneNumber;
	private List<String>orderDetails;
	private double totalPrice;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(mappedBy = "bill")
	@JsonIgnore
	private Order order;

}
