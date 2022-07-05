package com.example.school.repository;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.school.model.NewUserToken;

public interface NewUserTokenRepository extends JpaRepository<NewUserToken, Long> {
		public Optional<NewUserToken> findByTokenAndEmailIgnoreCase(String token, String email);
}
