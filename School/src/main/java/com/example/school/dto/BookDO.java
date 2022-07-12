package com.example.school.dto;

import java.util.List;

import javax.persistence.OneToMany;

import com.example.school.model.BookCopy;

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
public class BookDO {
	private long id;
	private String name;
	private String auther;
	private String publication;
	private long ISBN;
	private int pages;
	private int publication_year;
}
