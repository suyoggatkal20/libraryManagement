package com.example.school.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.school.dto.BookCopyCreateDO;
import com.example.school.dto.BookCopyDO;
import com.example.school.dto.BookDO;
import com.example.school.dto.PagedResultDO;
import com.example.school.exception.EmptyDataException;
import com.example.school.exception.InvalidInputsExceptions;
import com.example.school.model.Book;
import com.example.school.model.BookCopy;
import com.example.school.repository.AddressRepository;
import com.example.school.repository.BookCopyRepository;
import com.example.school.repository.BookRepository;
import com.example.school.repository.NewUserTokenRepository;
import com.example.school.repository.RoleRepository;
import com.example.school.repository.TokenRepository;
import com.example.school.repository.UserRepository;
import com.example.school.security.JwtHelper;
import com.example.school.util.CommonBeans;

@Service
public class BookService {

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

	@Value("${data.maxsize}")
	private int maxDataSize;


	public BookDO addBook(BookDO bookDO) {
		ModelMapper modelMapper = new ModelMapper();
		Book book = modelMapper.map(bookDO, Book.class);
		try {
			bookRepository.save(book);
		} catch (DataIntegrityViolationException e) {
				throw new InvalidInputsExceptions("Duplicate ISBN number./n This book is already present");
		}
		bookDO = modelMapper.map(book, BookDO.class);
		return bookDO;
	}
	@Transactional
	public BookCopyDO addBookCopy(BookCopyCreateDO bookCopyCreateDO) {
		ModelMapper modelMapper = new ModelMapper();
		BookCopy bookCopy = modelMapper.map(bookCopyCreateDO, BookCopy.class);
		Optional<Book> book=bookRepository.findByISBN(bookCopyCreateDO.getISBN());
		if(!book.isPresent()) {
			throw new InvalidInputsExceptions("Provided ISBN number is not present in system.");
		}
		bookCopy.setBook(book.get());
		bookCopy.setAvailable(true);
		bookCopy.setDisposed(false);
		bookCopyRepository.save(bookCopy);
		return modelMapper.map(bookCopy, BookCopyDO.class);
	}
	public PagedResultDO<BookCopyDO> getAllBooks(Integer page, Integer size) {
		Page<BookCopy> page1= bookCopyRepository.findAll(PageRequest.of(page-1, size));
		if(!page1.hasContent()) {
			throw new EmptyDataException("No books found");
		}
		ModelMapper mapper=new ModelMapper();
		List<BookCopyDO> bookCopyList = mapper.map(page1.getContent(), new TypeToken<List<BookCopyDO>>(){}.getType());
		PagedResultDO<BookCopyDO> res=new PagedResultDO(page,size,page1.getTotalElements(),page1.getTotalPages(),bookCopyList);
		return res;
	}
	

}
