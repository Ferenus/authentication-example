package com.huro.service;

import com.huro.model.entity.HuroUser;
import com.huro.model.repository.UserRepository;
import com.huro.security.jwt.JwtService;
import com.huro.support.DateGenerator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtService jwtService;
    private DateGenerator dateGenerator;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService, DateGenerator dateGenerator) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.dateGenerator = dateGenerator;
    }

    public void create(String firstName, String lastName, String username, String password) {
        userRepository.save(new HuroUser(firstName, lastName, username, bCryptPasswordEncoder.encode(password), "USER", null));
    }

    public HuroUser isLoginValid(String username, String pass) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(pass)) {
            return null;
        }
        String password = new String(Base64.decodeBase64(pass.getBytes()));
        HuroUser u = userRepository.findByUsername(username);
        if (u == null) {
            return null;
        }
        if (!bCryptPasswordEncoder.matches(password, u.getPassword())) {
            return null;
        }
        return u;
    }

    public HuroUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public HuroUser createUserToken(String username, String secret) {
        String token = jwtService.createToken(username, secret, dateGenerator.getExpirationDate());
        HuroUser u = userRepository.findByUsername(username);
        u.setToken(token);
        return userRepository.save(u);
    }

    public HuroUser validateUser(String token, String secret) {
        String username = jwtService.getUsername(token, secret);
        if (username != null) {
            HuroUser user = userRepository.findByUsername(username);
            if (user != null && token.equals(user.getToken()) && jwtService.isValid(token, secret)) {
                return user;
            }
        }
        return null;
    }
}
