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
