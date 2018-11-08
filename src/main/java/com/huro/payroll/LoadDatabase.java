package com.huro.payroll;

import com.huro.model.entity.HuroUser;
import com.huro.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, UserRepository urepository) {
		return args -> {
			log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
			log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
			HuroUser huroUser1 = new HuroUser("Dan", "S","user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			HuroUser huroUser2 = new HuroUser("Dan", "Str","admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			log.info("Preloading " + urepository.save(huroUser1));
			log.info("Preloading " + urepository.save(huroUser2));
		};
	}
}
