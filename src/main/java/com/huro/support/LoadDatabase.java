package com.huro.support;

import com.huro.service.UserService;
import org.springframework.stereotype.Component;

@Component
class LoadDatabase {

	public LoadDatabase(UserService userService) {
		userService.create("Dan", "S","user", "user");
		userService.create("Dan", "Str","tomcat", "tomcat");
	}
}
