package com.assignment.quizzy.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String question;

    @NotBlank
    private String subject;

    @NotBlank
    private String questionType;

    @NotBlank
    private String difficulty;

    @Valid
    @ElementCollection
    private List<String> choices;

    @Valid
    @ElementCollection
    private List<String> correctAnswers;

//    public List<String> getCorrectAnswers() {
//        return correctAnswers;
//    }
//
//    public void setCorrectAnswers(List<String> correctAnswers) {
//        this.correctAnswers = correctAnswers;
//    }
//
//    public List<String> getChoices() {
//        return choices;
//    }
//
//    public void setChoices(List<String> choices) {
//        this.choices = choices;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(String question) {
//        this.question = question;
//    }
}