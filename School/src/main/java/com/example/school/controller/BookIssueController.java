package com.example.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.school.dto.BookCopyDO;
import com.example.school.dto.PagedResultDO;
import com.example.school.repository.BookCopyRepository;
import com.example.school.repository.BookRepository;
import com.example.school.repository.RoleRepository;
import com.example.school.repository.UserRepository;
import com.example.school.service.AuthService;
import com.example.school.service.BookIssueService;
import com.example.school.service.BookService;
import com.example.school.service.UserService;
import com.example.school.util.CommonBeans;

@RestController
@RequestMapping("/api/v1/book-issue")
public class BookIssueController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookIssueService bookIssueService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@PostMapping("/book-issue-request")
	ResponseEntity bookIssueRequest(@RequestParam Long bookCopyId) {
		bookIssueService.issueBookRequest(bookCopyId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PostMapping("/approve-issue-request")
	ResponseEntity approveBookRequest(@RequestParam Long issueId) {
		bookIssueService.approveBookRequest(issueId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
	
}
