package com.example.school.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tokens")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String email;
	
	private String token;
	
	@Column(name = "generation_time")
	private Timestamp generationTime;
	
	@Column(name = "expiration_time")
	private Timestamp expirationTime;
	
	@Column(name = "is_valid")
	private boolean isValid;
	
	
	
	public boolean isValidAndNotExpired() {
		return getExpirationTime().after(new java.util.Date())&&isValid();
	}



	public Token(String email, String token, Timestamp generationTime, Timestamp expirationTime, boolean isValid) {
		super();
		this.email = email;
		this.token = token;
		this.generationTime = generationTime;
		this.expirationTime = expirationTime;
		this.isValid = isValid;
	}
	
}
