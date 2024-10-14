package com.sky.homework.module.project.controller.mapper;

import com.sky.homework.module.project.controller.response.ProjectResponse;
import com.sky.homework.module.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

	public ProjectResponse map(Project project) {
		return new ProjectResponse(project.getId(), project.getKey(), project.getName());
	}
}
