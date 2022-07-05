package com.example.school.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.school.model.User;
import com.example.school.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public User getLoggedInUser() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || !authentication.isAuthenticated() ||
				authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		Optional<User> user= userRepository.findByEmailIgnoreCase(authentication.getName());
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserDetails userDetails2=new User("suyog", "Gatkal", new HashSet<GrantedAuthority>());
		Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(username);
		if (optionalUser.isPresent()) {
			User user=optionalUser.get();
//		    String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities
			return getUserDetailsFromUser(user);
		}else {
			throw new  UsernameNotFoundException("User not found for email: "+username);
		}
	}
	
	public UserDetails getUserDetailsFromUser(User user) {
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
		return userDetails;
	}

}
