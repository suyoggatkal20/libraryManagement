package com.example.school.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.school.dto.BookCopyDO;
import com.example.school.dto.PagedResultDO;
import com.example.school.exception.InvalidInputsExceptions;
import com.example.school.model.Book;
import com.example.school.model.BookCopy;
import com.example.school.model.Issue;
import com.example.school.model.User;
import com.example.school.repository.BookCopyRepository;
import com.example.school.repository.BookRepository;
import com.example.school.repository.IssueRepository;
import com.example.school.repository.RoleRepository;
import com.example.school.repository.UserRepository;
import com.example.school.util.CommonBeans;

@Service
public class BookIssueService {
	@Autowired
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CommonBeans commonBeans;

	@Autowired
	private BookCopyRepository bookCopyRepository;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private IssueRepository issueRepository;

	@Value("${data.maxsize}")
	private int maxDataSize;

	@Value("${days.for.book}")
	private long daysForBook;
	
	@Transactional
	public void issueBookRequest(Long bookCopyId) {
		long userId=userService.getLoggedInUser().getId();
		Optional<BookCopy> bookCopyOptional=bookCopyRepository.findById(bookCopyId);
		if(!bookCopyOptional.isPresent()||bookCopyOptional.get().isDisposed()) {
			throw new InvalidInputsExceptions("Provided Book id is not valid");
		}
		BookCopy bookCopy=bookCopyOptional.get();
		if(!bookCopy.isAvailable()) {
			throw new InvalidInputsExceptions("Book of provided id is currently not available");
		}
		Issue issue= new Issue();
		issue.set_request_cancelled(false);
		issue.setBook_id(bookCopyId);
		issue.setUser_id(userId);
		issue.setRequest_timestamp(new Timestamp(System.currentTimeMillis()));
		issueRepository.save(issue);
	}
@Transactional
	public void approveBookRequest(Long issueId) {
		Optional<Issue> issueOptional=issueRepository.findById(issueId);
		if(!issueOptional.isPresent()) {
			throw new InvalidInputsExceptions("Invalid issue Id. Please try again.");
		}
		
		Issue issue=issueOptional.get();
		BookCopy bookcopy=bookCopyRepository.findById(issue.getBook_id()).get();
		if(!bookcopy.isAvailable()||bookcopy.isDisposed()) {
			throw new InvalidInputsExceptions("Book is currently not available.");
		}
		bookcopy.setAvailable(false);
		bookCopyRepository.save(bookcopy);
		issue.setIssuer_id(userService.getLoggedInUser().getId());
		issue.setIssue_timestamp(new Timestamp(System.currentTimeMillis()));
		issue.setReturn_before(new Date(System.currentTimeMillis()+1000*60*60*24*daysForBook));
		issueRepository.save(issue);
	}
}