package com.a1.insecureswe;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
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
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        System.out.println("LoginAttemptService for failure reached");
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException ex) {
            attempts = 0;
        }
        System.out.println("Attempts for IP increased: " + key);
        attempts++;
        System.out.println("Current Attempts for key " + key + " = " + attempts);
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        System.out.println("isBlocked in LoginAttemptService reached");
        try {
            System.out.println("isBlocked = " + (attemptsCache.get(key) >= MAX_ATTEMPT));
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException ex) {
            System.out.println("isBlocked -- Execution Exception returned, IP not blocked");
            return false;
        }
    }
}
