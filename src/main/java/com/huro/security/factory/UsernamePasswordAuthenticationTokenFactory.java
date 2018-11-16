package com.huro.security.factory;


import com.huro.model.entity.HuroUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UsernamePasswordAuthenticationTokenFactory {

    public UsernamePasswordAuthenticationToken create(HuroUser u) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(u.getRole());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), Arrays.asList(simpleGrantedAuthority));
        return authentication;
    }

}
