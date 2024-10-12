package com.sky.homework.module.user.service;

import com.sky.homework.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByEmail(String email);

}
