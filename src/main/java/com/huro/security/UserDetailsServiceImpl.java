package com.huro.security;

import com.huro.model.entity.HuroUser;
import com.huro.model.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HuroUser applicationHuroUser = applicationUserRepository.findByUsername(username);
        if (applicationHuroUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationHuroUser.getUsername(), applicationHuroUser.getPasswordHash(), emptyList());
    }
}
