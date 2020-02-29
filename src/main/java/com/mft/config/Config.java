package com.mft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fattahi.general.dozer.JsonViewSupportFactoryBean;

@Configuration
public class Config {
	@Bean
	public JsonViewSupportFactoryBean jsonViewSupportFactoryBean() {
		JsonViewSupportFactoryBean jsonViewSupportFactoryBean = new JsonViewSupportFactoryBean();
		return jsonViewSupportFactoryBean;
	}

}
