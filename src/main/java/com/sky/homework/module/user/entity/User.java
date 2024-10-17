package com.sky.homework.module.user.entity;

import com.sky.homework.module.project.entity.Project;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "tb_user")
@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	private UUID id;

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private String name;

	@ManyToMany
	@JoinTable(
			name = "tb_user_external_project",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "external_project_id"))
	private List<Project> projects = new ArrayList<>();
}
