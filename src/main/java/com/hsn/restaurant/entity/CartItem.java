package com.hsn.restaurant.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

	@Id
	@GeneratedValue
	private Long id;
	
	private double totalPrice;
	private int quantity;
	@ManyToOne
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
}
