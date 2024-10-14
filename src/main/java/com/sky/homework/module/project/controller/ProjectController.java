package com.sky.homework.module.project.controller;

import com.sky.homework.module.project.controller.mapper.ProjectMapper;
import com.sky.homework.module.project.controller.request.CreateProjectRequest;
import com.sky.homework.module.project.controller.request.UpdateProjectRequest;
import com.sky.homework.module.project.controller.response.ProjectResponse;
import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.service.ProjectFinderService;
import com.sky.homework.module.project.service.ProjectService;
import com.sky.homework.module.project.service.command.CreateProjectCommand;
import com.sky.homework.module.project.service.command.UpdateProjectCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectFinderService projectFinderService;
	private final ProjectService projectService;
	private final ProjectMapper projectMapper;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProjectResponse create(@RequestBody CreateProjectRequest request) {
		final Project project = projectService.create(new CreateProjectCommand(request.key(), request.name()));
		return projectMapper.map(project);
	}

	@PutMapping(value="/{id}")
	public ProjectResponse update(@PathVariable UUID id, @RequestBody UpdateProjectRequest request) {
		final Project project = projectService.update(new UpdateProjectCommand(id, request.key(), request.name()));
		return projectMapper.map(project);
	}

	@GetMapping(value = "/{id}")
	public ProjectResponse get(@PathVariable UUID id) {
		return projectMapper.map(projectFinderService.getById(id));
	}
}
