package com.company.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Question {
    private Scanner input;
    private String description;
    private HashMap<Integer, String> choices;
    private int[] answer;
    private boolean isCorrect = false; // default as false

    int[] getAnswer() { return answer; }
    Scanner getInput() { return input; }
    public boolean isCorrect() { return isCorrect; }
    public String getDescription() { return description; }


    void setCorrect() { isCorrect = true; }
    void setChoices(HashMap<Integer, String> choices) { this.choices = choices; }

    Question() {}

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
    int getResponse() {
        // array will initialize with 0
        int response = 0;
        boolean incorrect = true;
        do {
            System.out.println("Enter the number corresponding to your choice");
            displayChoices();

            if (this.getInput().hasNextInt()) {
                response = this.getInput().nextInt();
                incorrect = false;
            } else {
                this.getInput().next(); // clears invalid input
            }
        } while (incorrect);

        return response;
    }
//
//    int[] getResponses() {
//        // collect multiple responses from user
//        ArrayList<Integer> responses = new ArrayList<>();
//        for (int i = 0; i < choices.size(); ++i) {
//            int response = this.getResponse();
//            if (responses.contains(response)) {
//                System.out.println("Already selected this option");
//                continue;
//            }
//
//            responses.add(response);
//        }
//
//        return responses.toArray();
//    }

    // top level check if response and answer are same length
    // iterates over response and checks if answer array contains
    void evaluate(int response) {
        if (response == this.answer[0]) {
            this.setCorrect();
            System.out.println("CORRECT!");
        }  else {
            System.out.println("INCORRECT");
        }
    }

    void evaluate(int[] responses) {
        // evaluate multiple reponses against answers
    }
}
