package com.cooksys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cooksys.dto.PMCountDto;
import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectManagerMapper;
import com.cooksys.repository.ProjectManagerRepository;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectManagerService {

	private ProjectManagerRepository repo;
	private ProjectRepository pRepo;
	private ProjectManagerMapper mapper;

	public ProjectManagerService(ProjectManagerRepository repo, ProjectRepository pRepo, ProjectManagerMapper mapper) {
		this.repo = repo;
		this.pRepo = pRepo;
		this.mapper = mapper;
	}
	
	public List<ProjectManagerDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public boolean has(Long id) {
		return repo.exists(id);
	}

	public ProjectManagerDto get(Long id) {
		mustExist(id);
		return mapper.toDto(repo.findOne(id));
	}

	public Long post(ProjectManagerDto projectManagerDto) {
		projectManagerDto.setId(null);
		return repo.save(mapper.toEntity(projectManagerDto)).getId();
	}

	public void put(Long id, ProjectManagerDto projectManagerDto) {
		mustExist(id);
		projectManagerDto.setId(id);
		repo.save(mapper.toEntity(projectManagerDto));
	}
	
	public Set<Project> getProjects(Long id) {
		ProjectManager manager = repo.findByIdEquals(id);
		return manager.getProjects();
	}
	
	public List<PMCountDto> getAllOverdue() {
		Date currDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		List<ProjectManager> managerList = repo.findAll(); 
		List<PMCountDto> countSet = new ArrayList<>();
		for (ProjectManager manager : managerList) {
			List<Project> projectList = pRepo.findByManagerAndDueDateLessThan(manager, currDate);
			if (projectList.size() > 0) {
				PMCountDto currDto = new PMCountDto();
				currDto.setFirstName(manager.getFirstName());
				currDto.setLastName(manager.getLastName());
				currDto.setOverdue(projectList.size());
				System.out.println(manager.getFirstName());
				countSet.add(currDto);
			}
		}
		countSet.sort(Comparator.comparing(PMCountDto::getOverdue).reversed());
		return countSet;
	}

	private void mustExist(Long id) {
		if(!has(id))
			throw new ReferencedEntityNotFoundException(ProjectManager.class, id);
	}

	public void delete(Long id) {
		mustExist(id);
		repo.delete(id);
	}
}
