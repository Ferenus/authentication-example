package com.huro.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class HuroUser {

	private @Id @GeneratedValue Long id;
	private String email; //unique
	private String password;
	private String role;
	private String token; //nullable

	public HuroUser(){

	}

	public HuroUser(String email, String password, String role, String token) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.token = token;
	}
}
