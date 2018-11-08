package com.huro.model.repository;

import com.huro.model.entity.HuroUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<HuroUser, Long> {
    HuroUser findByUsername(String username);
}
