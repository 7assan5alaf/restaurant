package com.hsn.restaurant.entity;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails,Principal {
	
	@Id
	@GeneratedValue
	private Long id;
	private String fullName;
	@Column(unique = true)
	private String email;
	private String password;
	private String phoneNumber;
	private String address;
	private boolean enable;
	private String role;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createdDate;
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Code>codes;
    
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<Order>orders;
	@OneToMany(mappedBy = "user")
	private List<Bill>bills;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enable;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return email;
	}

}
