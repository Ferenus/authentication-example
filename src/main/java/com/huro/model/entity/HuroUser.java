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
	private String username;
	private String passwordHash;
	private String role;

	public HuroUser(){

	}

	public HuroUser(String firstName, String lastName, String username, String passwordHash, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}
}
