package com.a1.insecureswe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    LoginAttemptService loginAttemptService;

    public CustomAuthenticationFailureHandler() { }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws ServletException, IOException {
        setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);

        String errorMessage = "Invalid username or password";
        System.out.println("Made it to invalid credentials error message");
        System.out.println(exception.getMessage());

        if (loginAttemptService.isBlocked(request.getRemoteAddr())) {
            System.out.println("Made it to blocked error message");
            errorMessage = "Your IP is blocked from logging in for 1 hour";
        }
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }


}
