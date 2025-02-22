package com.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rid;

	private Integer communication;

	private Integer confidence;

	private Integer interaction;

	private Integer liveliness;

	private Integer usageProps;

	private Double totalScore = 0.0;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "presentation_id")
	private Presentation presentation;
	
	

}
