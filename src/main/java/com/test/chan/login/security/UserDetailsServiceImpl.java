package com.test.chan.login.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.test.chan.login.dao.UserDAO;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// get user by username
		String password = UserDAO.getUserByLogin(username);
		if (null == password) {
			throw new UsernameNotFoundException("User " + username + " not exists");
		}
		// get user roles and build user authorities
		List<String> roles = UserDAO.getUserRoles(username);
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new GrantedAuthorityImpl(role));
		}
		// instanciate Spring Security class User
		return new UserDetailsImpl(username, password, authorities);
	}
	
	public UserDetails loadUserByUsernamePassword(String userName, String password) {
		
		
		
		
		return null;
	}

}
