package com.example.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.school.dto.ErrorResponceDO;

import io.jsonwebtoken.SignatureException;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(value = SchoolException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponceDO handleSchoolException(SchoolException schoolException) {
		return new ErrorResponceDO(HttpStatus.INTERNAL_SERVER_ERROR.value(), schoolException.getMessage());
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponceDO handleUserNotFound(UserNotFoundException userNotFoundException) {
		return new ErrorResponceDO(HttpStatus.UNAUTHORIZED.value(), userNotFoundException.getMessage());
	}
	
	@ExceptionHandler(value = UserDisabledException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponceDO handleUserDisabledException(UserDisabledException userDisabledException) {
		return new ErrorResponceDO(HttpStatus.BAD_REQUEST.value(), userDisabledException.getMessage());
	}
	
	@ExceptionHandler(value = SignatureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponceDO handleJwtSignatureException(SignatureException jwtSignatureException) {
		return new ErrorResponceDO(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token");
	}
	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponceDO handleAuthenticationException(AuthenticationException authenticationException) {
		return new ErrorResponceDO(HttpStatus.BAD_REQUEST.value(), "Username or password is incorrect");
	}
	
	@ExceptionHandler(value = UnauthorizedUserException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponceDO handleUnauthorizedUserException(AuthenticationException authenticationException) {
		return new ErrorResponceDO(HttpStatus.UNAUTHORIZED.value(),authenticationException.getMessage());
	}
}
