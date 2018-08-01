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
        int response = super.getResponse();
        super.evaluate(response);
    }
}
