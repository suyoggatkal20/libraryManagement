package com.example.school.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.school.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
		public Optional<Token> findByToken(String token);
		public List<Token> findByEmail(String email);

		@Modifying
		@Query("delete from tokens where expiration_time<:now")
		public void deleteExpired(@Param("now") Timestamp now);
		
}
