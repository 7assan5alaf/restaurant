package com.hsn.restaurant.entity;

import java.time.LocalDateTime;



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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Code {

	@Id
	@GeneratedValue
	private Long id;
	private String code;
	
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	private LocalDateTime validatedAt;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
