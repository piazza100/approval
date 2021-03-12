package com.approval.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.approval.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder encoder() {
		return new MessageDigestPasswordEncoder("SHA-256");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
       	http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
               .exceptionHandling().authenticationEntryPoint( unauthorizedHandler ).and()
               .authorizeRequests()
               .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
               .antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
               .antMatchers("/api/approval/admin/**").hasAnyAuthority("ROLE_ADMIN")
               .antMatchers("/api/approval/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
               .antMatchers(
                       "/robots.txt",
                       "/engine.io/",
                       "/api/**",
                       "/",
                       "/error/**",
                       "/*.html",
                       "/favicon.ico",
                       "/**/*.html",
                       "/**/*.css",
                       "/**/*.js"
               ).permitAll()
               .anyRequest().authenticated()
               .and()
               .cors()
               .and()
    			.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
               ;
           http.csrf().disable();
	}
}