package com.hsn.restaurant.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hsn.restaurant.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	
	private long totalAmount;
	private String orderStatus;
	private String address;
	private double totalItem;
	private double totalPrice;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "order")
	private List<OrderItem>items;
	
	@OneToOne
	@JsonIgnore
	private Bill bill;
	
	
}
