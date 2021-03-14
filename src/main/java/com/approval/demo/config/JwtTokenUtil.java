package com.approval.demo.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.approval.demo.domain.UserVO;
import com.approval.demo.utils.CryptoUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 30;
	public static final String SIGNING_KEY = "approval1234";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String ISS_USER = "approval";

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserVO user) throws Exception {
		return doGenerateToken(user);
	}

	private String doGenerateToken(UserVO user) throws Exception {
		Claims claims = Jwts.claims().setSubject(CryptoUtil.AESencrypt(user.getUserId()));

		if ("USER".equals(user.getRole())) {
			claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		} else {
			claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		}

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISS_USER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		try {
			username = CryptoUtil.AESdecrypt(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
