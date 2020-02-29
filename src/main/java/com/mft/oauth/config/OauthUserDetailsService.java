package com.mft.oauth.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mft.oauth.model.User;
import com.mft.oauth.service.IUserService;

@Service
public class OauthUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ReflectionSaltSource saltSource;

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private static final Logger LOGGER = Logger.getLogger(OauthUserDetailsService.class);

	@SuppressWarnings("deprecation")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User userEntity = null;
		userEntity = userService.findByUserName(username);
		String password = request.getParameter("password");
		if (userEntity == null) {
			request.getSession().setAttribute("exceptionLogin", username);
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		} else {
			boolean passWrong = true;
			org.springframework.security.authentication.encoding.PasswordEncoder passwordEncoder = new ShaPasswordEncoder(
					256);
			
			
			Object salt = null;
			if (this.saltSource != null) {
				salt = this.saltSource.getSalt(userEntity);
			}
			passwordEncoder.encodePassword("123456", salt);
			passWrong = passwordEncoder.isPasswordValid(userEntity.getPassword(), password, salt);
			if (!passWrong) {
				request.getSession().setAttribute("exceptionLogin", password);
				request.getSession().setAttribute("showCaptcha", true);
				LOGGER.info("------------==passworng");
			}
		}
		return userEntity;
	}
}
