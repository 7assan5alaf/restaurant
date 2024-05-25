package com.hsn.restaurant.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${apllication.security.secret-key}")
	private String secretKey;
	@Value("${application.security.expiration-time}")
	private long jwtExpiration;

	public String extracteUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimsResolve) {
	 final 	Claims claims = getAllClaims(token);
		return claimsResolve.apply(claims);
	}

	@SuppressWarnings("deprecation")
	private Claims getAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				;
	}

	public String generateToken(HashMap<String, Object> claims, UserDetails userDetails) {
		return buildToken(claims, userDetails, jwtExpiration);
	}

	@SuppressWarnings("deprecation")
	private String buildToken(Map<String, Object> claims, UserDetails userDetails, long jwtExpiration) {
		var authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		return Jwts.builder().setClaims(claims).subject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.claim("authorities", authorities)
				.signWith(getKey()).compact();
	}

	public boolean isValidToken(String token, UserDetails details) {
		var username = extracteUsername(token);
		return (username.equals(details.getUsername()) && !isTokenExpiration(token));
	}

	private boolean isTokenExpiration(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	private Key getKey() {
		byte[] sginKey = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(sginKey);
	}
}
