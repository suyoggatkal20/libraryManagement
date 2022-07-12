package com.example.school.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "issue")
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long book_id;
	private long user_id;
	private Timestamp request_timestamp;
	private boolean is_request_cancelled;
	private long issuer_id;
	private Timestamp issue_timestamp;
	private Date return_before;
	private long collector_id;
	private Timestamp returned_timestamp;
}
