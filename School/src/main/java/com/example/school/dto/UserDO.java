package com.example.school.dto;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.example.school.model.Address;
import com.example.school.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class UserDO {
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
	private Date dateOfBirth;
	private String contact1;
	private String contact2;
	private String qualification;
	@JsonInclude
	private AddressDO permanentAddress;
	@JsonInclude
	private AddressDO temporaryAddress;
	private boolean enabled;
}
