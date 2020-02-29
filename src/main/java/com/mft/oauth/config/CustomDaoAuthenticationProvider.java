package com.mft.oauth.config;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	public CustomDaoAuthenticationProvider() {

	}

	public CustomDaoAuthenticationProvider(UserDetailsService userDetailsService, Object passwordEncoder,
			SaltSource saltSource) {
		super.setUserDetailsService(userDetailsService);
		super.setPasswordEncoder(passwordEncoder);
		super.setSaltSource(saltSource);
	}

}
