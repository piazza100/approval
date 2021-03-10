package com.approval.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.approval.demo.utils.LoggerBeanPostProcessor;

@Configuration
public class LogConfig {
	@Bean
	public LoggerBeanPostProcessor logger() {
		return new LoggerBeanPostProcessor();
	}
}
