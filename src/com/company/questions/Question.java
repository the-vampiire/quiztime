package com.company.questions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Question {
    private Scanner input;
    private String description;
    private HashMap<Integer, String> choices;
    private int[] answer;
    private boolean isCorrect = false; // default as false

    public String getDescription() { return description; }
    int[] getAnswer() { return answer; }
    Scanner getInput() { return input; }
    public boolean isCorrect() { return isCorrect; }

    void setCorrect() { isCorrect = true; }
    void setChoices(HashMap<Integer, String> choices) { this.choices = choices; }

    Question() {};

    public Question(
        String description,
        HashMap<Integer,String> choices,
        int[] answer,
        Scanner input
    ) {
        this.description = description;
        this.choices = choices;
        this.answer = answer;
        this.input = input;
    }

    Question(
        String description,
        int[] answer,
        Scanner input
    ) {
        this.description = description;
        this.answer = answer;
        this.input = input;
    }

    void displayChoices() {
        for (Map.Entry<Integer, String> item : this.choices.entrySet()) {
            System.out.print(item.getKey() + ": " + item.getValue() + "\n");
        }
    }

    // sys out ask question
    // call getResponse()
    // call evaluate passing response array
    // flip isCorrect boolean
    public abstract void ask();

    // do-while to extract responses
    // break on end of choices or user option to complete
    abstract int[] getResponse();

    // top level check if response and answer are same length
    // iterates over response and checks if answer array contains
    abstract void evaluate(int[] response);
}
