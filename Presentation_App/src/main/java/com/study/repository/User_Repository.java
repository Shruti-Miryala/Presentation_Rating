package com.study.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.entity.User;
import com.study.enums.Role;

@Repository
public interface User_Repository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	
	List<User> findByRole(Role role);

}
