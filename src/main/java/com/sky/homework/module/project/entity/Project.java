package com.sky.homework.module.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
