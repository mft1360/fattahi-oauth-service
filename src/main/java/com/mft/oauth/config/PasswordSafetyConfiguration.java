package com.mft.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordSafetyConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ReflectionSaltSource saltSource() {
		ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
		reflectionSaltSource.setUserPropertyToUse("userName");
		return reflectionSaltSource;
	}

}