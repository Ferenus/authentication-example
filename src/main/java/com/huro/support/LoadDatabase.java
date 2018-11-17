package com.huro.support;

import com.huro.service.UserService;
import org.springframework.stereotype.Component;

@Component
class LoadDatabase {

	public LoadDatabase(UserService userService) {
		userService.create("user@gmail.com", "user");
		userService.create("tomcat@gmail.com", "tomcat");
	}
}
