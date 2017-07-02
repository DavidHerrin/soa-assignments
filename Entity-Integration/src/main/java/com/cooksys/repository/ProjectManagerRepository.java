package com.cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.ProjectManager;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long>{
	
	ProjectManager findByFirstNameAndLastNameEquals(String firstname, String lastname);
	
	ProjectManager findByIdEquals(Long id);

}
