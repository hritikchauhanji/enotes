package com.enotes.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enotes.entity.User;

public class CustomUserDetails implements UserDetails{

	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authority = new ArrayList<>();
		user.getRoles().forEach(r->{
			authority.add(new SimpleGrantedAuthority(r.getName()));
		});
		
		return authority;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
