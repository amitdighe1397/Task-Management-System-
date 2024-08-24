package com.example.config;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication auth) {
		Set<String> authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		String roles = populateAuthorities(authorities);

		String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86403030))
				.claim("email", auth.getName()).claim("authorities", roles).signWith(key).compact();
		return jwt;
	}

	public String getEmailFromJwtTokan(String jwt) {

		jwt = jwt.substring(7);

		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

		String email = String.valueOf(claims.get("email"));

		return email;
	}

	private String populateAuthorities(Set<String> authorities) {
		return String.join(",", authorities);
	}
}
