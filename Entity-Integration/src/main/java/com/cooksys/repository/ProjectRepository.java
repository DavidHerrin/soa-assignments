package com.cooksys.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;


public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<Project> findByDueDateLessThan(Date date);
	
	List<Project> findByManagerAndDueDateLessThan(ProjectManager manager, Date date);

}
