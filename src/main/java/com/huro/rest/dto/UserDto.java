package com.huro.rest.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class UserDto {

	private @Id @GeneratedValue Long id;
	private String email;
	private String password;

	public UserDto(){

	}

	public UserDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
