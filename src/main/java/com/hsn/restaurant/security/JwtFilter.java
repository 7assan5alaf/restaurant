package com.hsn.restaurant.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final MyUserDetailsService detailsService;

	@Override
	protected void doFilterInternal(
			@Nonnull HttpServletRequest request, 
			@Nonnull HttpServletResponse response, 
			@Nonnull FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getContextPath().contains("/fried-chicken/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth == null || !auth.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = auth.substring(7);
		String email = jwtService.extracteUsername(token);
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails details = detailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(details,
					null, details.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);

	}

}
