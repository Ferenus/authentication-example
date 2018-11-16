package com.huro.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class HuroUser {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String username; //unique
	private String password;
	private String role;
	private String token; //nullable

	public HuroUser(){

	}

	public HuroUser(String firstName, String lastName, String username, String password, String role, String token) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.token = token;
	}
}
