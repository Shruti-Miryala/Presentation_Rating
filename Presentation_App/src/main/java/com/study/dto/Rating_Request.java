package com.study.dto;

import com.study.entity.Presentation;
import com.study.entity.User;

import lombok.Data;

@Data
public class Rating_Request {

	private Integer communication;

	private Integer confidence;

	private Integer interaction;

	private Integer liveliness;

	private Integer usageProps;

	private Double TotalScore;

	private User user;
	
	private Presentation presentation;

}
