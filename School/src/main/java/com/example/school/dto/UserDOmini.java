package com.example.school.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDOmini {
	@NotNull
	private long id;
	@NotBlank
	private String email;
	@NotBlank
	private String roles;
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
}
