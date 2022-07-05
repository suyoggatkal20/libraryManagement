package com.example.school.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.school.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
		public Optional<Role> findByCode(String code);
}
