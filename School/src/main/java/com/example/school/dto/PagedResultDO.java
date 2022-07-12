package com.example.school.dto;

import java.util.List;

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
public class PagedResultDO<T> {
	private long page;
	private long size;
	private long totalElements;
	private long totalPages;
	private List<T> elements;
}
