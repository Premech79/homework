package com.sky.homework.module.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.homework.module.project.controller.mapper.ProjectMapper;
import com.sky.homework.module.project.controller.request.CreateProjectRequest;
import com.sky.homework.module.project.controller.request.UpdateProjectRequest;
import com.sky.homework.module.project.controller.response.ProjectResponse;
import com.sky.homework.module.project.entity.Project;
import com.sky.homework.module.project.service.ProjectFinderService;
import com.sky.homework.module.project.service.ProjectService;
import com.sky.homework.module.project.service.command.CreateProjectCommand;
import com.sky.homework.module.project.service.command.UpdateProjectCommand;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private ProjectService projectService;

	@MockBean
	private ProjectFinderService projectFinderService;

	@MockBean
	private ProjectMapper projectMapper;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@SneakyThrows
	public void shouldCreateProject() {
		Project project = new Project();
		project.setId(UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"));
		project.setKey("KEY");
		project.setName("Project name");

		ProjectResponse response = new ProjectResponse(
				UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"),
				"KEY",
				"Project name"
		);

		CreateProjectRequest request = new CreateProjectRequest(
				"KEY",
				"Project name"
		);

		given(projectService.create(any(CreateProjectCommand.class))).willReturn(project);
		given(projectMapper.map(any(Project.class))).willReturn(response);

		mockMvc.perform(post("/projects")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985")))
				.andExpect(jsonPath("$.key", is("KEY")))
				.andExpect(jsonPath("$.name", is("Project name")));

		ArgumentCaptor<CreateProjectCommand> commandCaptor = ArgumentCaptor.forClass(CreateProjectCommand.class);
		verify(projectService).create(commandCaptor.capture());
	}

	@Test
	@SneakyThrows
	public void shouldUpdateProject() {
		Project project = new Project();
		project.setId(UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"));
		project.setKey("New KEY");
		project.setName("New project name");

		ProjectResponse response = new ProjectResponse(
				UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"),
				"New KEY",
				"New project name"
		);

		UpdateProjectRequest request = new UpdateProjectRequest("New KEY", "New project name");
		UUID id = UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985");

		given(projectService.update(any(UpdateProjectCommand.class))).willReturn(project);
		given(projectMapper.map(any(Project.class))).willReturn(response);

		mockMvc.perform(put("/projects/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985")))
				.andExpect(jsonPath("$.key", is("New KEY")))
				.andExpect(jsonPath("$.name", is("New project name")));

		ArgumentCaptor<UpdateProjectCommand> commandCaptor = ArgumentCaptor.forClass(UpdateProjectCommand.class);
		verify(projectService).update(commandCaptor.capture());
	}

	@Test
	@SneakyThrows
	public void shouldGetProject() {
		Project project = new Project();
		project.setId(UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"));
		project.setKey("KEY");
		project.setName("Project name");

		ProjectResponse response = new ProjectResponse(
				UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985"),
				"KEY",
				"Project name"
		);

		UUID id = UUID.fromString("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985");
		given(projectFinderService.getById(id)).willReturn(project);
		given(projectMapper.map(any(Project.class))).willReturn(response);

		mockMvc.perform(get("/projects/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("7b3ef0d1-2122-44d2-9ae5-1c0cdbce4985")))
				.andExpect(jsonPath("$.key", is("KEY")))
				.andExpect(jsonPath("$.name", is("Project name")));

		verify(projectFinderService).getById(id);
	}
}