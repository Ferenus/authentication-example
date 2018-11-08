package com.huro.rest.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class UserDto {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;

	public UserDto(){

	}

	public UserDto(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
}