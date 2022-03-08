package com.a1.insecureswe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "forum")
@Table(name = "forum")
public class Forum {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String question;

    @Column
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
