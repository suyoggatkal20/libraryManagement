package com.example.school.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "new_user")
public class NewUserToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String token;
	
	private String email;
	
	@Column(name = "account_type")
	private String accountType;
	
	private Timestamp expiry;
	
	@Column(name = "created_timestamp")
	private Timestamp createdTimestamp;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	public NewUserToken(String token, String email, String accountType, Timestamp expiry, Timestamp createdTimestamp,
			User createdBy) {
		super();
		this.token = token;
		this.email = email;
		this.accountType = accountType;
		this.expiry = expiry;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
	}
	
	public boolean isExprired() {
		return !expiry.after(new Timestamp(System.currentTimeMillis()));
	}
	
	
	

}
