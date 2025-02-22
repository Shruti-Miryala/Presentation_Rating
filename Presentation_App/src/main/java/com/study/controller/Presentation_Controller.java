package com.study.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.entity.Presentation;
import com.study.enums.PresentationStatus;
import com.study.service.Presentation_Service;

@Controller
public class Presentation_Controller {

	private Presentation_Service presentation_service;

	public Presentation_Controller(Presentation_Service presentation_service) {
		this.presentation_service = presentation_service;
	}

//	--------------------------------------- assign presentation ---------------------------

	@PutMapping("/assign/{adminId}")
	public ResponseEntity<String> assignPresentation(@PathVariable Integer adminId, @RequestParam Integer studentId,
			@RequestParam String course, @RequestParam String topic,
			@RequestParam PresentationStatus presentationStatus) {

		String response = presentation_service.assignPresentationToStudent(adminId, studentId, course, topic,
				presentationStatus);
		if (response.contains("assigned")) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.badRequest().body(response);
	}

//-------------------------------------- fetch presentation through presentationId ---------------------------------------
	@GetMapping("/presentation/{pid}")
	public ResponseEntity<Presentation> fetchPresentationByPid(@PathVariable Integer pid) {
		Presentation presentation = presentation_service.getPresentation(pid);
		return ResponseEntity.ok(presentation);
	}

//	-------------------------------------- get all presentations based on student id ------------------------

	@GetMapping("/byUser/{uid}")
	public ResponseEntity<List<Presentation>> getAllPresentationsByUser(@PathVariable Integer uid) {
		List<Presentation> presentations = presentation_service.getAllPresentationsByUserId(uid);
		return ResponseEntity.ok(presentations);
	}

//	-------------------------------------- change presentationStatus ---------------------------------------

	@PutMapping("/updatePresentationStatus/{uid}")
	public ResponseEntity<String> updatePresentationStatus(@PathVariable Integer uid, @RequestParam Integer pid,
			@RequestParam PresentationStatus presentationStatus) {

		boolean success = presentation_service.changePresentationStatus(uid, pid, presentationStatus);
		if (success) {
			return ResponseEntity.ok("Presentation status updated successfully.");
		}
		return ResponseEntity.badRequest().body("Failed to update presentation status.");
	}

}
