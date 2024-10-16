package com.sky.homework.module.project.entity;

import com.sky.homework.module.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "tb_external_project")
@Entity
@Data
@NoArgsConstructor
public class Project {

	@Id
	private UUID id = UUID.randomUUID();

	private String key;

	private String name;

	@ManyToMany(mappedBy = "projects")
	private List<User> users = new ArrayList<>();
}
