package com.huro.security.filter;


import com.huro.model.entity.HuroUser;
import com.huro.security.SecurityAppContext;
import com.huro.security.factory.UsernamePasswordAuthenticationTokenFactory;
import com.huro.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String UTF_8 = "UTF-8";
    private static final int BEGIN_INDEX = 7;
    private final Log logger = LogFactory.getLog(this.getClass());

    private final UserService userService;
    private final UsernamePasswordAuthenticationTokenFactory usernamePasswordAuthenticationTokenFactory;
    private final SecurityAppContext securityAppContext;

    public JwtAuthenticationTokenFilter(UserService userService, UsernamePasswordAuthenticationTokenFactory usernamePasswordAuthenticationTokenFactory, SecurityAppContext securityAppContext) {
        this.userService = userService;
        this.usernamePasswordAuthenticationTokenFactory = usernamePasswordAuthenticationTokenFactory;
        this.securityAppContext = securityAppContext;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(AUTHORIZATION);
        if(authToken != null) {
            try {
                authToken = new String(authToken.substring(BEGIN_INDEX).getBytes(), UTF_8);
                SecurityContext context = securityAppContext.getContext();
                if(context.getAuthentication() == null) {
                    logger.info("Checking authentication for token " + authToken);
                    HuroUser u = userService.validateUser(authToken, request.getRemoteAddr());
                    if(u != null) {
                        logger.info("User " + u.getUsername() + " found.");
                        Authentication authentication = usernamePasswordAuthenticationTokenFactory.create(u);
                        context.setAuthentication(authentication);
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                logger.error(e.getMessage());
            }

        }
        chain.doFilter(request, response);
    }

}
