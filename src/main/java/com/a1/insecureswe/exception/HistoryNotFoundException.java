package com.a1.insecureswe.exception;

public class HistoryNotFoundException extends Exception {
    public HistoryNotFoundException(long user_id) {
        super(String.format("History is not found for user : '%s'", user_id));
    }
}
