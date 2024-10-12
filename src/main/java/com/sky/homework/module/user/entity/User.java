package com.sky.homework.module.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "tb_user")
@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	private final UUID id = UUID.randomUUID();

	private String email;

	private String password;

	private String name;

}
