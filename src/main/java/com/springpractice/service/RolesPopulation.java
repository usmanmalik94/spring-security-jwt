package com.springpractice.service;

import org.springframework.stereotype.Service;

import com.springpractice.domain.Roles;

@Service
public class RolesPopulation {
	private final String ADMIN = "admin";

	public String processRoles(String role) {
		if (role.toLowerCase().contains(ADMIN.toLowerCase())) {
			return Roles.ROLE_ADMIN.toString();
		} else {
			return Roles.ROLE_USER.toString();
		}
	}

}
