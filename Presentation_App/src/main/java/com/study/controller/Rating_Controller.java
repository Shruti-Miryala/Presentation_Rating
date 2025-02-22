package com.study.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.dto.Rating_Request;
import com.study.entity.Rating;
import com.study.service.Rating_Service;

@Controller
public class Rating_Controller {

	private Rating_Service rating_service;

	public Rating_Controller(Rating_Service rating_service) {
		this.rating_service = rating_service;
	}

//	----------------- rate the presentation ------------------------
	@PostMapping("/ratePresentation/{uid}")
	public ResponseEntity<String> addRatings(@PathVariable Integer uid, @RequestParam Integer pid,
			@RequestBody Rating_Request rating) {
		System.out.println(rating);
		boolean isRated = rating_service.ratePresentation(uid, pid, rating);

		if (isRated) {
			return ResponseEntity.ok("Presentation rated successfully");
		} else {
			return ResponseEntity.badRequest()
					.body("Prsentation with given uid: " + uid + "and with pid: " + pid + "not found");
		}

	}

//	------------------- fetch the rating of particular student ------------------------
	@GetMapping("/getRating")
    public ResponseEntity<?> fetchRating(@RequestParam Integer pid, @RequestParam Integer uid) {
        Rating rating = rating_service.getRating(pid, uid);
        if (rating != null) {
            return ResponseEntity.ok(rating); // Found the rating
        } else {
            return ResponseEntity.badRequest().body("Rating not found for presentationId: " 
                                                    + pid + " and studentId: " + uid);
        }
    }
	
	
	
	

}
