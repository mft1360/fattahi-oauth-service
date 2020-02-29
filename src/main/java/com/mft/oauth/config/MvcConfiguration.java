package com.mft.oauth.config;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mft.oauth.model.User;

@Configuration
@RestController
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private CustomDefaultTokenServices customDefaultTokenServices;

	@Bean
	FilterRegistrationBean forwardedHeaderFilter() {
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		filterRegBean.setFilter(new ForwardedHeaderFilter());
		filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filterRegBean;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/oauth/confirm_access").setViewName("authorize");
	}

	@RequestMapping("/login")
	public ModelAndView getlogin(Model model, HttpServletResponse response, HttpServletRequest request) {
		if (request.getSession().getAttribute("exceptionLogin") != null) {
			request.getSession().setAttribute("showErrorLogin", request.getSession().getAttribute("exceptionLogin"));
			request.getSession().removeAttribute("exceptionLogin");
		} else {
			request.getSession().removeAttribute("showErrorLogin");
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/logout1", method = RequestMethod.DELETE)
	public void logout(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.contains("bearer")) {
			String tokenId = authorization.substring("Bearer".length() + 1);
			customDefaultTokenServices.revokeToken(tokenId);
		}
	}

	@RequestMapping(value = "/currentuser", method = RequestMethod.GET)
	public Principal getCurrentUser(Principal user) {
		return user;
	}

	@RequestMapping(value = "/customcurrentuser", method = RequestMethod.GET)
	public User getCustomCurrentUser(Principal user) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) user;
		User retUser = (User) oAuth2Authentication.getPrincipal();
		return retUser;
	}

}
