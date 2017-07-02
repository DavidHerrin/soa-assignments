package com.cooksys.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.dto.ProjectDto;
import com.cooksys.entity.Project;
import com.cooksys.service.ProjectService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("project")
public class ProjectController {
	
	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@GetMapping
	@ApiOperation(value = "", nickname = "getAllProjects")
	public List<ProjectDto> getAll() {
		return projectService.getAll();
	}
	
	@GetMapping("overdue")
	@ApiOperation(value = "", nickname = "getOverdueProjects")
	public List<Project> getOverdue() {
		return projectService.getOverdue();
	}
	
	@PostMapping
	@ApiOperation(value = "", nickname = "postNewProject")
	public Long post(@RequestBody @Validated ProjectDto projectDto, HttpServletResponse httpResponse) {
		Long id = projectService.post(projectDto);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return id;
	}
	
	@DeleteMapping("{id}")
	@ApiOperation(value = "", nickname = "deleteProjectAtId")
	public void delete(@PathVariable Long id, HttpServletResponse httpResponse) {
		projectService.delete(id);
	}

}
