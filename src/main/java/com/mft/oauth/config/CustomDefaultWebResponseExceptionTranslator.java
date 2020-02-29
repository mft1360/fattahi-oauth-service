package com.mft.oauth.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

public class CustomDefaultWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		InvalidGrantException invalidGrantException = (InvalidGrantException) e;
		System.out.println(invalidGrantException.getMessage());
		ResponseEntity<OAuth2Exception> response = super.translate(e);
		return response;
	}

}
