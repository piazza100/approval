package com.approval.demo.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class UserVO implements UserDetails {
	@JsonIgnore
	private Integer userNo;

	private String userId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private Date regTime;
	private String role;

	public static final class USER_ROLE {
		public static final String USER = "USER";
		public static final String ADMIN = "ADMIN";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if ("USER".equals(this.role)) {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

//
//CREATE TABLE `approval`.`user` (
//`user_no` int(11) NOT NULL AUTO_INCREMENT,
//`user_id` varchar(45) NOT NULL,
//`password` varchar(200) NOT NULL,
//`role` varchar(45) DEFAULT NULL,
//`reg_time` datetime NOT NULL,
//`mod_time` datetime NOT NULL,
//PRIMARY KEY (`user_no`),
//UNIQUE KEY `user_id_UNIQUE` (`user_id`)
//) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
//;
