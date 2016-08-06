package com.test.chan.login.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7279608678446934980L;
	
	private String role;
	
	public GrantedAuthorityImpl(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}

}
