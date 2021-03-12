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
	private Integer userNo;
	private String userId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@JsonIgnore
	private Date regTime;
	@JsonIgnore
	private String role;

	public static final class USER_ROLE {
		public static final String USER = "USER";
		public static final String ADMIN = "ADMIN";
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if ("USER".equals(this.role)) {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return this.userId;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}