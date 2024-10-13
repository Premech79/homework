package com.sky.homework.module.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Table(name = "tb_user")
@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	private final UUID id = UUID.randomUUID();

	@Column(nullable = false, unique = true, updatable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private String name;

	private Instant deletedAt;
}
