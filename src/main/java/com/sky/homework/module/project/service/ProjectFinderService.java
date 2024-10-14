package com.sky.homework.module.project.service;

import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.exception.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectFinderService {

	private final ProjectRepository projectRepository;

	@Transactional(readOnly = true)
	public boolean existsByKey(String key) {
		return projectRepository.existsByKey(key);
	}

	@Transactional(readOnly = true)
	public Project getById(UUID id) {
		return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
	}
}
