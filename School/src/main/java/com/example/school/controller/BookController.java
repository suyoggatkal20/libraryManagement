package com.example.school.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.school.dto.BookCopyCreateDO;
import com.example.school.dto.BookCopyDO;
import com.example.school.dto.BookDO;
import com.example.school.dto.PagedResultDO;
import com.example.school.repository.UserRepository;
import com.example.school.service.AuthService;
import com.example.school.service.BookService;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
		
		@Autowired
		UserRepository userRepository;
		
		@Autowired
		private AuthService authService;
		
		@Autowired
		private BookService bookService;
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		@GetMapping("/book")
		PagedResultDO<BookCopyDO> getBooks(@RequestParam (defaultValue = "1") Integer page,@RequestParam (defaultValue = "10") Integer size) {
			return bookService.getAllBooks(page,size);
			
		}
		
		@PostMapping("/add-book")
		public ResponseEntity<BookDO> addBookController(@RequestBody  @Validated BookDO bookDO) {
			bookDO=bookService.addBook(bookDO);
			return new ResponseEntity(bookDO, HttpStatus.CREATED);
		}
		@PostMapping("/add-book-copy")
		public ResponseEntity<BookCopyDO>  addBookCopy(@RequestBody  @Validated BookCopyCreateDO bookCopyCreateDO){
			BookCopyDO bookCopyDO=bookService.addBookCopy(bookCopyCreateDO);
			return new ResponseEntity<BookCopyDO>(bookCopyDO,HttpStatus.CREATED);
		}
		
		
}
