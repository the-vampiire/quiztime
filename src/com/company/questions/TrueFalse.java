package com.company.questions;

import java.util.HashMap;
import java.util.Scanner;

public class TrueFalse extends Question {
    public TrueFalse(
        String description,
        int[] answer,
        Scanner input
    ) {
        super(description, answer, input);

        HashMap<Integer, String> choices = new HashMap<>();
        choices.put(1, "True");
        choices.put(2, "False");

        this.setChoices(choices);

    }

    public void ask() {
        System.out.println(this.getDescription() + "\n");
        int[] response = getResponse();
        evaluate(response);
    }

    int[] getResponse() {
        // array will initialize with 0
        int[] response = new int[1];
        boolean incorrect = true;
        do {
            System.out.println("Enter the number corresponding to your choice");
            super.displayChoices();

            if (this.getInput().hasNextInt()) {
                response[0] = this.getInput().nextInt();
                incorrect = false;
            } else {
                this.getInput().next(); // clears invalid input
            }
        } while (incorrect);

        return response;
    }

    void evaluate(int[] response) {
        if (response[0] == getAnswer()[0]) {
            setCorrect();
            System.out.println("CORRECT!");
        }  else {
            System.out.println("INCORRECT");
        }
    }
}
