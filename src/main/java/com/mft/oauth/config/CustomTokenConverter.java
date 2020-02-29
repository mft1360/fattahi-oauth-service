package com.mft.oauth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomTokenConverter extends  JwtAccessTokenConverter /*TokenEnhancerChain*/ {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Map<String, Object> additionalInfo = new HashMap<>();
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return super.enhance(accessToken, authentication);
	}

}