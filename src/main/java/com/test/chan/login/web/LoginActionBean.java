package com.test.chan.login.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.chan.login.dto.UserDto;
import com.test.chan.login.security.UserDetailsServiceImpl;
import com.test.chan.login.security.UserService;

@ManagedBean(name = "loginActionBean")
@SessionScoped
public class LoginActionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2772430579147098204L;

	private UserDto userDto;
	@PostConstruct
	public void init() {
		userDto = new UserDto();
	}

	public String login() {
		UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUserDto().getUserName());

		if (null == userDetails.getPassword()) {
			return "/login.xhtml?faces-redirect=true";
		}
		if (!getUserDto().getPassword().equals(userDetails.getPassword())) {
			return "/login.xhtml?faces-redirect=true";
		}

		return "/welcome.xhtml?faces-redirect=true";
	}

	/**
	 * the login action called by the view
	 * 
	 * @return
	 */
	public String logintest() {
		try {
			// LOG.info("Login started for User with Name:
			// "+getUserDto().getUserName());

			// check if userdata is given
			if (getUserDto().getUserName() == null || getUserDto().getPassword() == null) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "login.failed");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				// LOG.info("Login not started because userName or Password is
				// empty: "+getUserDto().getUserName());
				return "login";
			}

			UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUserDto().getUserName());

			// authenticate afainst spring security
			Authentication request = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
					userDetails.getPassword(), userDetails.getAuthorities());

			//Authentication result = authenticationManager.authenticate(request);
			//Authentication result = this.authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(request);

		} catch (AuthenticationException e) {
			// LOG.info("Login failed: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "login.failed");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			return "login";
		}
		return "welcome";

	}
	
//	public String doLogin() {
//		userService.getUserCount(getUserDto().getUserName(), getUserDto().getPassword());
//		return null;
//	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}
