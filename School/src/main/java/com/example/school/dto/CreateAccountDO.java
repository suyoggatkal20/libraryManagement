package com.example.school.dto;

import java.sql.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import com.example.school.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateAccountDO {
	@NotBlank
	private String email;
	
	@NotBlank
	private String token;
	
	@NotBlank
	private String password;
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String dateOfBirth;
	@NotBlank
	private String contact1;
	private String contact2;
	private String qualification;
	@NotNull
	private AddressDO permanentAddress;
	@NotNull
	private AddressDO temporaryAddress;
}
