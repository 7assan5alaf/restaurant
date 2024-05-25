package com.hsn.restaurant.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hsn.restaurant.entity.User;


public class ApplicationAuditorWare implements AuditorAware<String> {
	
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null||!authentication.isAuthenticated()
				
				|| authentication instanceof AnonymousAuthenticationToken) {
			
			return Optional.empty();
		}
	
		User user=(User)authentication.getPrincipal();			
			
		return Optional.ofNullable(user.getUsername());
	}


}
