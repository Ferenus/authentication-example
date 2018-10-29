package com.huro.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

	private @Id @GeneratedValue Long id;
	private String name;
	private String role;

	User(String name, String role) {
		this.name = name;
		this.role = role;
	}
}
