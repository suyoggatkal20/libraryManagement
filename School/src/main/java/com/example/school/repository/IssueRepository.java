package com.example.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.school.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{

}
