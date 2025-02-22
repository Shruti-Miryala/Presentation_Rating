package com.study.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.study.dto.Rating_Request;
import com.study.entity.Presentation;
import com.study.entity.Rating;
import com.study.entity.User;
import com.study.repository.Presentation_Repository;
import com.study.repository.Rating_Repository;
import com.study.repository.User_Repository;

@Service
public class Rating_Service {

	private Rating_Repository rating_repository;

	private Presentation_Repository presentation_repository;

	private User_Repository user_repository;

	public Rating_Service(Rating_Repository rating_repository, Presentation_Repository presentation_repository,
			User_Repository user_repository) {
		this.rating_repository = rating_repository;
		this.presentation_repository = presentation_repository;
		this.user_repository = user_repository;
	}

//	--------------------------------- rate the presentation ---------------------
	public boolean ratePresentation(Integer uid, Integer pid, Rating_Request rating_request) {

		Optional<Presentation> opt = presentation_repository.findByStudentUidAndPid(uid, pid);

		if (opt.isPresent()) {
			Presentation presentation = opt.get();
			User user = presentation.getStudent();

			rating_request.setUser(user);
			rating_request.setPresentation(presentation);

			// Calculate the total score based on the rating fields
			double totalScore = (rating_request.getCommunication() + rating_request.getConfidence()
					+ rating_request.getInteraction() + rating_request.getLiveliness() + rating_request.getUsageProps())
					/ 5.0; // Assuming the average of all ratings

			rating_request.setTotalScore(totalScore);

			// Save the rating
			Rating rating = new Rating();
			BeanUtils.copyProperties(rating_request, rating);
			rating_repository.save(rating);

			// Update the userTotalScore in the Presentation entity
			presentation.setUserTotalScore(totalScore);
			presentation_repository.save(presentation);
			
			updateUserTotalScore(user);
			

			return true;
		}
		return false;
	}
	
//	----------------------- method to calculate the userToatalScore -----------------------------

	private void updateUserTotalScore(User user) {
		// Fetch all presentations assigned to the student
		List<Presentation> presentations = presentation_repository.findByStudentUid(user.getUid());

		// Initialize variables to calculate sum and count
		double totalScoreSum = 0.0;
		int presentationCount = 0;

		// Loop through all presentations and add their scores
		for (Presentation presentation : presentations) {
			totalScoreSum += presentation.getUserTotalScore();
			presentationCount++;
		}

		// Calculate average (handle division by zero case)
		double averageScore = presentationCount > 0 ? totalScoreSum / presentationCount : 0.0;

		// Update the user's total score
		user.setUserTotalScore(averageScore);
		user_repository.save(user);
	}

//	--------------------------------------------- fetch the rating of particular student ----------------------------------

	public Rating getRating(Integer pid, Integer uid) {
		return rating_repository.findByPresentationPidAndUserUid(pid, uid).orElse(null); // Return null if no rating is
																							// found
	}
}
