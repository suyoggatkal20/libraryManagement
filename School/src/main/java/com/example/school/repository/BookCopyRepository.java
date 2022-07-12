package com.example.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.school.dto.BookCopyDO;
import com.example.school.model.BookCopy;

public interface BookCopyRepository extends PagingAndSortingRepository<BookCopy, Long> {

}
