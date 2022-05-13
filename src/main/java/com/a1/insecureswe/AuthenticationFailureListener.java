package com.a1.insecureswe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        System.out.println("Made it to AuthenticationFailureListener");
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if(xfHeader == null) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
            System.out.println("Header is null, attempt failed, IP sent to LoginAttemptService");
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
        }
    }
}
