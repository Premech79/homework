package com.sky.homework.module.project.service;

import com.sky.homework.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ProjectRepository extends JpaRepository<Project, UUID> {

	boolean existsByKey(String key);

}
