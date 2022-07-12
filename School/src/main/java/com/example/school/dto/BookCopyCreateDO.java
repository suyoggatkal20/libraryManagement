package com.example.school.dto;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookCopyCreateDO {

	@NotNull
	private Long bookId;
	
	@NotNull
	private Long ISBN;

	@NotNull
	private Date purchaseDate;

	@NotNull
	private int rackNo;
	
	@NotNull
	private int prise;
}
