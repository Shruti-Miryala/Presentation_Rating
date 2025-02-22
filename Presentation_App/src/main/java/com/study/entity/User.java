package com.study.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.enums.Role;
import com.study.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;
	
	private String name;

	@Column(unique = true)
	private String email;
	
	private Long phone;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private Double userTotalScore = 0.0;
	
	@OneToMany(mappedBy = "assignedBy", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Presentation> assignedPresentations; // Presentations assigned by this user (admin)

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Presentation> receivedPresentations; // Presentations assigned to this user (student)

}
