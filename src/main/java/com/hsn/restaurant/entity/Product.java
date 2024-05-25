package com.hsn.restaurant.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hsn.restaurant.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product")
@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

	private String name;
	private String synopsis;
	private String cover;
	private boolean availabilty;
	private double price;
	@ManyToOne
	@JoinColumn(name = "categorey_id")
	@JsonIgnore
	private Categorey categorey;
	
	@OneToMany(mappedBy = "product",orphanRemoval = true)
	@JsonIgnore
	private List<OrderItem>items;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<CartItem>cartItems;
}
