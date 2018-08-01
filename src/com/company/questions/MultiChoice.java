package com.company.questions;

import java.util.HashMap;
import java.util.Scanner;

// TODO: implement this class
public class MultiChoice extends Question {
    public MultiChoice(
        String description,
        HashMap<Integer, String> choices,
        int[] answer,
        Scanner input
    ) {
        super(description, choices, answer, input);
    }

    public MultiChoice(String description, int[] answer, Scanner input) {
        super(description, answer, input);
    }

    public void ask() {
        System.out.println(this.getDescription() + "\n");
        int response = super.getResponse();
        super.evaluate(response);
    }
}
