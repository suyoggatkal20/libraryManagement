package com.example.school.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "roles")
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String code;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<User> users;

	@Override
	public String getAuthority() {
		return this.code;
	}

	@Override
	public boolean equals(Object obj) {
		 if(obj instanceof Role) {
			return this.getCode().equals(((Role)obj).getCode());
		}
		return false;
	}
	

	

	public Role(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	@Override
	public int hashCode() {
		return this.getCode().hashCode();
	}
	
	
	
}
