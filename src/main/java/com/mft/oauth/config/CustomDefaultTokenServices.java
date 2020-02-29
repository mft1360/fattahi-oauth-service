package com.mft.oauth.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import com.mft.oauth.model.User;

public class CustomDefaultTokenServices extends DefaultTokenServices {

	@Override
	public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
		OAuth2AccessToken oAuth2AccessToken = super.createAccessToken(authentication);
		User user = (User) authentication.getPrincipal();
		oAuth2AccessToken.getAdditionalInformation().put("user", user);
		return oAuth2AccessToken;
	}

}
