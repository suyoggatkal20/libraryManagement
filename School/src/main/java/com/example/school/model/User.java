/**
 * 
 */
package com.example.school.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;

import com.example.school.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	
	@JsonIgnore
	@ToString.Exclude
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name="role_id")
	)
	private Set<Role> roles;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "middle_name")
	private String middleName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	private String contact1;
	private String contact2;
	private String qualification;
	@OneToOne
	@JoinColumn(name = "permanent_address_id")
	private Address permanentAddress;
	
	
	@OneToOne
	@JoinColumn(name = "temporary_address_id")
	private Address temporaryAddress;
	private boolean enabled;
	
	public boolean isAdmin() {
		return roles.contains(new Role("admin", Constants.ROLE_ADMIN));
	}
	public boolean isLibrarian() {
		return roles.contains(new Role("librarian", Constants.ROLE_LIBRARIAN));
	}
	public boolean isUser() {
		return roles.contains(new Role("user", Constants.ROLE_USER));
	}
}
