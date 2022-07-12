package com.example.school.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.school.model.Book;

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
public class BookCopyDO {
	
	private long id;

	@NotNull
	private BookDO book;

	@NotNull
	private Date purchaseDate;

	private boolean isDisposed;

	private boolean isAvailable;

	@NotNull
	private int rackNo;
	
	@NotEmpty
	private int prise;
	
}
