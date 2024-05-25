package com.hsn.restaurant.entity;

import java.util.List;

import com.hsn.restaurant.base.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Categorey extends BaseEntity {
	
	@Column(unique = true)
	private String name;
	
	@OneToMany(mappedBy = "categorey",cascade = CascadeType.ALL)
	private List<Product>products;
	

}
