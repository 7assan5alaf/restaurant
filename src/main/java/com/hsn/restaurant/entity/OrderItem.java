package com.hsn.restaurant.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hsn.restaurant.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class OrderItem extends BaseEntity {

	@ManyToOne()
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;
	@ManyToOne()
	@JsonIgnore
	private Order order;
	private double totalPrice;
	private int quantity;
}
