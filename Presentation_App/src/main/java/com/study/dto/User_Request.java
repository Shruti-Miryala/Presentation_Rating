package com.study.dto;

import com.study.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User_Request {
	
	private String name;
	
	private String email;
	
	private Long phone;
	
	private String password;
	
	private Role role;

}
