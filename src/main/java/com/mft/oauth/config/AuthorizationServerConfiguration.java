package com.mft.oauth.config;


import java.security.KeyPair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private OauthUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(this.authenticationManager)
				.tokenEnhancer(jwtAccessTokenConverter());
		endpoints.exceptionTranslator(getWebResponseExceptionTranslator());
		endpoints.tokenServices(tokenServices());
		endpoints.userDetailsService((UserDetailsService) userDetailsService);
		endpoints.addInterceptor(new HandlerInterceptorAdapter() {
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				if (modelAndView != null && modelAndView.getView() instanceof RedirectView) {
					RedirectView redirect = (RedirectView) modelAndView.getView();
					String url = redirect.getUrl();
					if (url.contains("code=") || url.contains("error=")) {
						HttpSession session = request.getSession(false);
						if (session != null) {
							session.invalidate();
						}
					}
				}
			}
		});
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.passwordEncoder(passwordEncoder);
		oauthServer.tokenKeyAccess("isAuthenticated()").checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.jdbc(dataSource);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public CustomTokenConverter jwtAccessTokenConverter() {
		CustomTokenConverter converter = new CustomTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("identity.jks"), "Admin@123".toCharArray())
				.getKeyPair("mohsen");
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Bean(name = "exceptionTranslator")
	public CustomDefaultWebResponseExceptionTranslator getWebResponseExceptionTranslator() {
		CustomDefaultWebResponseExceptionTranslator customExceptionTranslator = new CustomDefaultWebResponseExceptionTranslator();
		return customExceptionTranslator;
	}

	@Bean
	@Primary
	public CustomDefaultTokenServices tokenServices() {
		CustomDefaultTokenServices tokenServices = new CustomDefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		/* tokenServices.setAccessTokenValiditySeconds(60 * 60); */
		/* tokenServices.setSupportRefreshToken(true); */
		tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
		return tokenServices;
	}

}
