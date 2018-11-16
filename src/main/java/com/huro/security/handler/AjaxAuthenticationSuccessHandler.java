
package com.huro.security.handler;


import com.huro.model.entity.HuroUser;
import com.huro.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HeaderHandler headerHandler;
    private final UserService userService;

    public AjaxAuthenticationSuccessHandler(HeaderHandler headerHandler, UserService userService) {
        this.headerHandler = headerHandler;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.debug("Authentication Successful");
        HuroUser u = userService.createUserToken(authentication.getName(), request.getRemoteAddr());
        response.getWriter().print("{ \"token\" : \"" + u.getToken() + "\"}");
        response.setStatus(HttpServletResponse.SC_OK);
        headerHandler.process(request, response);
    }

}
