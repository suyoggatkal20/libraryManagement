package com.example.school.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.school.model.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
//	@Query(" select  FROM book b WHERE b.isbn in (:isbnList)")
//	public Set<Book> findByIsbnIn(@Param("isbnList") Set<Long> isbnList);
	public Optional<Book> findByISBN(Long isbn);
}
