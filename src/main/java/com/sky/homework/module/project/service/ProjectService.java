package com.sky.homework.module.project.service;

import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.exception.ProjectKeyExistsException;
import com.sky.homework.module.project.service.command.CreateProjectCommand;
import com.sky.homework.module.project.service.command.UpdateProjectCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class ProjectService {

	private final ProjectFinderService projectFinderService;
	private final ProjectRepository projectRepository;

	@Transactional
	public Project create(@Valid CreateProjectCommand command) {
		if (projectFinderService.existsByKey(command.key())) {
			throw new ProjectKeyExistsException();
		}

		Project project = new Project();
		project.setKey(command.key());
		project.setName(command.name());

		return projectRepository.save(project);
	}

	@Transactional
	public Project update(@Valid UpdateProjectCommand command) {
		if (projectFinderService.existsByKey(command.key())) {
			throw new ProjectKeyExistsException();
		}

		Project project = projectFinderService.getById(command.id());
		project.setKey(command.key());
		project.setName(command.name());

		return projectRepository.save(project);
	}
}
