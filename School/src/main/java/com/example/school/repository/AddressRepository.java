package com.example.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.school.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	
}
