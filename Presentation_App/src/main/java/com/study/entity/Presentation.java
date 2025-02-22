package com.study.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.enums.PresentationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Presentation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pid;
	
	private String course;
	
	private String topic; 
	
	@Enumerated(EnumType.STRING)
	private PresentationStatus presentationStatus;
	
	private Double userTotalScore = 0.0;
	
	@ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy; // Admin who assigns the presentation

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private User student; // Student who receives the presentation

}
