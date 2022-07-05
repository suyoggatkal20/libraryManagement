package com.example.school.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.example.school.constants.Constants;
import com.example.school.model.Role;
import com.example.school.repository.RoleRepository;

@Repository
public class CommonBeans {
	private Role adminRole;
	private Role userRole;
	private Role librarianRole;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	private void setRoles() {
		adminRole=roleRepository.findByCode(Constants.ROLE_ADMIN).get();
		userRole=roleRepository.findByCode(Constants.ROLE_USER).get();
		librarianRole=roleRepository.findByCode(Constants.ROLE_LIBRARIAN).get();
	}
	
	@Bean
	public Role getAdminRole() {
			return this.adminRole;
	}
	@Bean
	public Role getUserRole() {
			return this.userRole;
	}
	@Bean
	public Role getLibrarianRole() {
			return this.librarianRole;
	}
}
