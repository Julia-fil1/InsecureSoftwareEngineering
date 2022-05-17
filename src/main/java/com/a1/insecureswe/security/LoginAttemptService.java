package com.a1.insecureswe.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private static final Logger insecureLogger = LogManager.getLogger(LoginAttemptService.class);
    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
        insecureLogger.info("IP " + key + " has successfully logged in.");
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;

        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException ex) {
            attempts = 0;
        }
        attempts++;
        insecureLogger.info("IP " + key + " has failed to log in " + attempts + " times.");
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException ex) {
            return false;
        }
    }
}
