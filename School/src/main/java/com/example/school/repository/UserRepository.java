package com.example.school.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.school.model.User;

 
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmailIgnoreCase(String email);
}
