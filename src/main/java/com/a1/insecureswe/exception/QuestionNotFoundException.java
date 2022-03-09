package com.a1.insecureswe.exception;

public class QuestionNotFoundException extends Exception{
    public QuestionNotFoundException(long question_id) {
        super(String.format("Question is not found with id : '%s'", question_id));
    }
}
