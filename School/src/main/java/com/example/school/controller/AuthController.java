package com.example.school.controller;

import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.example.school.dto.AuthTokenDO;
import com.example.school.dto.LoginDO;
import com.example.school.exception.SchoolException;
import com.example.school.exception.UserDisabledException;
import com.example.school.model.User;
import com.example.school.repository.UserRepository;
import com.example.school.service.AuthService;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/login")
	public ResponseEntity<AuthTokenDO> loginController(@RequestBody LoginDO loginDo) throws SchoolException, UserDisabledException {
		logger.info("Start loginController");
		AuthTokenDO authTokenDO=authService.authenticate(loginDo);
		logger.info("End loginController");
		return ResponseEntity.ok(authTokenDO);
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<AuthTokenDO> signUpController(@RequestBody LoginDO loginDo) throws SchoolException, UserDisabledException {
		logger.info("Start signUpController");
		AuthTokenDO authTokenDO=authService.authenticate(loginDo);
		logger.info("End signUpController");
		return ResponseEntity.ok(authTokenDO);
	}
	
	@GetMapping("/all")
	public String all() {
		return "ALL suyog gatkal";
	}
	
	@GetMapping("/create-signup-token")
	public ResponseEntity createSignUpTokenController(@RequestParam String email, @RequestParam String type) {
		logger.info("Start createSignUpTokenController");
		authService.createSignUpToken(email, type);
		logger.info("End createSignUpTokenController");
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	
	@GetMapping("/logout")
	public ResponseEntity logoutController(@RequestHeader("Authorization") String authorization) {
		logger.info("Start logoutController");
		authService.logout(authorization);
		logger.info("End logoutController");
		return ResponseEntity.ok(HttpStatus.OK);
	}
	

}
