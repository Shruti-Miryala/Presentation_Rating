package com.study.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.study.entity.Presentation;
import com.study.entity.User;
import com.study.enums.PresentationStatus;
import com.study.enums.Role;
import com.study.repository.Presentation_Repository;
import com.study.repository.User_Repository;

@Service
public class Presentation_Service {

	private Presentation_Repository presentation_repository;

	private User_Repository user_repository;

	public Presentation_Service(Presentation_Repository presentation_repository, User_Repository user_repository) {
		this.presentation_repository = presentation_repository;
		this.user_repository = user_repository;
	}

	public String assignPresentationToStudent(Integer adminId, Integer studentId, String course, String topic,
			PresentationStatus presentationStatus) {
		Optional<User> optionalAdmin = user_repository.findById(adminId);
		Optional<User> optionalStudent = user_repository.findById(studentId);

		if (optionalAdmin.isPresent() && optionalStudent.isPresent()) {
			User admin = optionalAdmin.get();
			User student = optionalStudent.get();

			// Ensure the admin has the admin role
			if (!Role.ADMIN.equals(admin.getRole())) {
				return "User with ID " + adminId + " is not an admin.";
			}

			// Ensure the student has the student role
			if (!Role.STUDENT.equals(student.getRole())) {
				return "User with ID " + studentId + " is not a student.";
			}

			// Create and save the presentation
			Presentation presentation = new Presentation();
			presentation.setCourse(course);
			presentation.setTopic(topic);
			presentation.setPresentationStatus(presentationStatus);
			presentation.setAssignedBy(admin); // Set the admin who assigned the presentation
			presentation.setStudent(student); // Set the student who receives the presentation

			presentation_repository.save(presentation); // Save the presentation

			return "Presentation assigned successfully to student by ID " + adminId;
		}

		return "Admin or student not found.";
	}

//	-------------------------------fetch the presentation by pid--------------------------------

	public Presentation getPresentation(Integer pid) {
		return presentation_repository.findById(pid)
				.orElseThrow(() -> new RuntimeException("Presentation with ID " + pid + " not found"));
	}

//	------------------------------fetch all presentations using student id--------------------

	public List<Presentation> getAllPresentationsByUserId(Integer uid) {
		return presentation_repository.findAllByStudentUid(uid);
	}

//	---------------------------------- change presentation status --------------------------

	public boolean changePresentationStatus(Integer uid, Integer pid, PresentationStatus newStatus) {
		// Fetch the specific presentation
		Presentation presentation = presentation_repository.findByStudentUidAndPid(uid, pid)
				.orElseThrow(() -> new RuntimeException("Presentation not found for the given userId and pID."));

		// Update the status
		presentation.setPresentationStatus(newStatus);
		presentation_repository.save(presentation);

		return true;
	}

}
