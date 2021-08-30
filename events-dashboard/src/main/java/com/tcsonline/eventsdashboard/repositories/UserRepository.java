package com.tcsonline.eventsdashboard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcsonline.eventsdashboard.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByUsername(String username);
}
