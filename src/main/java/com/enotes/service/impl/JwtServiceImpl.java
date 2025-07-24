package com.enotes.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.enotes.entity.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
import com.enotes.exceptionhandling.JwtAuthenticationException;
import com.enotes.exceptionhandling.JwtTokenExpiredException;
import com.enotes.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private String secretKey = "";

	public JwtServiceImpl() {
		try {

			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		claims.put("mobNo", user.getMobNo());
		claims.put("roles", user.getRoles().stream().map(Role::getName).toList());
		claims.put("status", user.getStatus().getIsActive());
		claims.put("course", user.getCourse());
		claims.put("semester", user.getSemester());

		// 2 minutes = 2 * 60 * 1000 milliseconds
		String token = Jwts.builder().claims()
				.add(claims)
				.subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 15)) // 15 minutes
				.and()
				.signWith(getKey())
				.compact();

		return token;
	}


	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parser().verifyWith(decrytKey(secretKey)).build().parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			throw new JwtTokenExpiredException("Token is Expired...");
		} catch (JwtException e) {
			throw new JwtAuthenticationException("Invalid Jwt Token...");
		} catch (Exception e) {
			throw e;
		}
	}

	private SecretKey decrytKey(String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String role(String token) {
		Claims claims = extractAllClaims(token);
		return (String) claims.get("role");
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		Boolean isExpired = isTokenExpired(token);

		if (username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired) {
			return true;
		}
		return false;
	}

	private Boolean isTokenExpired(String token) {
		Claims claims = extractAllClaims(token);
		Date expiration = claims.getExpiration();
		// for expire date
		return expiration.before(new Date());
	}

}
