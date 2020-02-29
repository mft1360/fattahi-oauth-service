package com.mft.oauth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class FilterForCaptcha extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String captchaCode = request.getParameter("captchacode");
		checkCaptcha(request, captchaCode);
		Authentication authentication = super.attemptAuthentication(request, response);
		return authentication;
	}

	private void checkCaptcha(HttpServletRequest request, String captchaCode) {
		if (request.getSession().getAttribute("showCaptcha") == null) {
			return;
		}
		boolean captchaPassed = ImageCaptcha.validateResponse(request, captchaCode);
		if (!captchaPassed) {
			request.getSession().setAttribute("exceptionLogin", "error");
			String error = "AbstractUserDetailsAuthenticationProvider.badCaptcha";
			throw new BadCredentialsException(error);
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		request.getSession().removeAttribute("showCaptcha");
		request.getSession().removeAttribute("exceptionLogin");
	}

}
