package com.cooksys.service;


import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.ProjectManagerRepository;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectService {
	
	private ProjectRepository repo;
	private ProjectManagerRepository repoPM;
	private ProjectMapper mapper;

	public ProjectService(ProjectRepository repo, ProjectManagerRepository repoPM, ProjectMapper mapper) {
		this.repo = repo;
		this.repoPM = repoPM;
		this.mapper = mapper;
	}
	
	public List<ProjectDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}
	
	public List<Project> getOverdue() {
		Date currDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		return repo.findByDueDateLessThan(currDate);
	}

	public boolean has(Long id) {
		return repo.exists(id);
	}

	public ProjectDto get(Long id) {
		mustExist(id);
		return mapper.toDto(repo.findOne(id));
	}

	public Long post(ProjectDto projectDto) {
		
		ProjectManager manager = repoPM.findByFirstNameAndLastNameEquals(projectDto.getManager().getFirstName(), projectDto.getManager().getLastName());
		Project project = new Project();
		project.setManager(manager);
		project.setStartDate(projectDto.getStartDate());
		project.setDueDate(projectDto.getDueDate());
		project.setId(null);
		return repo.save(project).getId();
	}

	public void put(Long id, ProjectDto projectDto) {
		mustExist(id);
		projectDto.setId(id);
		repo.save(mapper.toEntity(projectDto));
	}
	
	private void mustExist(Long id) {
		if(!has(id))
			throw new ReferencedEntityNotFoundException(Project.class, id);
	}

	public void delete(Long id) {
		mustExist(id);
		repo.delete(id);
	}

}
