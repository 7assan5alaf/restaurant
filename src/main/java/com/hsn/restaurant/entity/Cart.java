package com.hsn.restaurant.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hsn.restaurant.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity {

	
	private double total;
	
	@OneToOne
	@JsonIgnore
	private User customer;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItem>cartItems;
}
