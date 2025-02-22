package com.study.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.study.dto.User_Request;
import com.study.entity.User;
import com.study.enums.Role;
import com.study.enums.Status;
import com.study.repository.User_Repository;

@Service
public class User_Service {

	private User_Repository user_repository;

	public User_Service(User_Repository user_repository) {
		this.user_repository = user_repository;
	}

//-----------------------------------------------------Register the user----------------------------------------------------------------------------------
	public boolean registerUser(User_Request request) {
		Optional<User> opt = user_repository.findByEmail(request.getEmail());

		if (opt.isPresent()) {
			return false;
		} else {
			User user = new User();
			BeanUtils.copyProperties(request, user);
			user_repository.save(user);
			return true;
		}
	}

//	-----------------------------------------------Login the User----------------------------------------------------------------------------?

	public boolean loginUser(String email, String password) {
		Optional<User> opt = user_repository.findByEmail(email);
		if (opt.isPresent() && opt.get().getPassword().equals(password)) {
			return true;
		}
		return false;
	}

//--------------------------------------------fetch student by admins's id --------------------------------------

	public List<User> getAllStudentsByAdminId(Integer uid) {
		// Fetch the user by ID
		User user = user_repository.findById(uid).orElseThrow(() -> new RuntimeException("User not found"));

		// Check if the user is an admin
		if (user.getRole() != Role.ADMIN) {
			throw new RuntimeException("Access is only for admins!");
		}

		// Fetch and return all users with the STUDENT role
		return user_repository.findByRole(Role.STUDENT);
	}

//	------------------------------------------change the statuses --------------------------------------

	public boolean changeStatus(Integer uid, Status status) {
		Optional<User> opt = user_repository.findById(uid);
		if (opt.isPresent()) {
			User user = opt.get();
			user.setStatus(status);
			user_repository.save(user);
			return true;
		}
		return false;
	}

}
