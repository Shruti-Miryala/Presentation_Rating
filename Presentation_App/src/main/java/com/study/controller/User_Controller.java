package com.study.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.dto.User_Request;
import com.study.entity.User;
import com.study.enums.Status;
import com.study.service.User_Service;

@CrossOrigin(origins = "http://127.0.0.1:5500")  // Allow CORS from this origin
@RestController
public class User_Controller {

	private User_Service user_service;

	public User_Controller(User_Service user_service) {
		this.user_service = user_service;
	}

//	-----------------------------------Register------------------------------------------------
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User_Request request) {
		boolean register = user_service.registerUser(request);

		if (register) {
			return ResponseEntity.ok("Registered Successfully");
		} else {
			return ResponseEntity.badRequest().body("Already registered");
		}
	}

// ------------------------------------Login-------------------------

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
		boolean login = user_service.loginUser(email, password);
		if (login) {
			return ResponseEntity.ok("Login successfully");
		} else {
			return ResponseEntity.badRequest().body("Invalid Credentials");
		}
	}

//-------------------------------------fetch student by admins's id ----------------------------------------
	@GetMapping("/students/{uid}")
	public ResponseEntity<?> getStudentsIfAdmin(@PathVariable Integer uid) {

		List<User> students = user_service.getAllStudentsByAdminId(uid);
		return ResponseEntity.ok(students);

	}

//	-----------------------change the status---------------------

	@PutMapping("/status/{uid}")
	public ResponseEntity<String> changeUserStatus(@PathVariable Integer uid, @RequestParam Status status) {
		boolean isupdated = user_service.changeStatus(uid, status);
		if (isupdated) {
			return ResponseEntity.ok("Status changed to " + status);
		}
		return ResponseEntity.badRequest().body("User not found");
	}

}
