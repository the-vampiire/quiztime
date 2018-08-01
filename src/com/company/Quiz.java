package com.company;

import com.company.questions.MultiChoice;
import com.company.questions.Question;
import com.company.questions.TrueFalse;

import java.util.*;

public class Quiz {
    private Scanner input;

    private ArrayList<Question> questions;
    private double grade;
    private String name;
    private int retryCount = 0;

    private String getName() {
        return name;
    }
    private void incrementRetryCount() { ++retryCount; }

    public static void demo(Scanner input) {
        boolean incomplete = true;
        do {
            System.out.println(">>>>>>>> QuizTime Demo! <<<<<<<\n");
            System.out.println("Enter a name for your quiz");
            try {
                String name = input.nextLine();
                Quiz quiz = new Quiz(name, input);
                incomplete = false;
                quiz.start();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for quiz name. Try again");
            }
        } while(incomplete);
    }

    public Quiz(String name, Scanner input) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.input = input;

        this.setupQuiz(); // initiate dialog for setting up a new Quiz
    }

    public void start()  {
        String intro = retryCount > 0 ? "Retry Attempt [" + retryCount + "] - " : "";
        System.out.println(">>>>> " + intro + "Quiz: " + this.getName() + " <<<<<<\n");

        int index = 1;
        for (Question question : this.questions) {
            if (retryCount > 0 && question.isCorrect()) continue;

            System.out.print("Question " + index + "/" + this.questions.size() + ": ");
            question.ask();
            ++index;
        }

        this.finish();
    }

    public double getGrade() {
        int totalCorrect = 0;
        for (Question question : this.questions) {
           if (question.isCorrect()) ++totalCorrect;
        }

        return (totalCorrect / this.questions.size()) * 100;
    }

    public void finish() {
        System.out.println("\n=========================\n");
        System.out.println("Grade: " + this.getGrade());
        if (retryCount > 0) System.out.println("Retry attempts taken: " + retryCount);
        System.out.println("\n>>>>> Missed Questions <<<<<<\n");

        int index = 0;
        for (Question question : this.questions) {
            if (!question.isCorrect()) {
                System.out.println("Question " + index + ": " + question.getDescription());
                ++index;
            }
        }

        if (index == 0) {
            System.out.println("No missed questions!");
        }

        System.out.println("\n>>>>> Missed Questions <<<<<<\n");

        if (index != 0) retry();
    }

    private void retry() {
        boolean incorrect = true;
        do {
            System.out.println("Retry missed questions?\n1: Yes\n2: No");
            int choice = this.input.nextInt();
            if (choice == 1) {
                incrementRetryCount();
                this.start();
                incorrect = false;
            } else {
                System.out.println("Better luck next time bro");
                incorrect = false;
            }
        } while (incorrect);
    }

    private void setupQuiz() {
        int numberOfQuestions;
        do {
            System.out.println("How many questions should be on the quiz?");
            if (this.input.hasNextInt()) {
                numberOfQuestions = this.input.nextInt();
            } else {
                this.input.next();  // clears the buffer
                numberOfQuestions = 0;
            }
        } while (numberOfQuestions == 0);

        for (int i = 0; i < numberOfQuestions; ++i) {
            this.addQuestion();
        }
    }

    private void addQuestion() {
        int choice;
        do {
            System.out.println("Which type of question do you want to build?");
            System.out.println("1: True or False\n2: Multi Choice\n3: Multi Select");

            if (this.input.hasNextInt()) {
                choice = this.input.nextInt();

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice");
                    choice = 0;
                }
            }  else {
                this.input.next();
                choice = 0;
            }
        } while (choice == 0);

        Question newQuestion = this.makeQuestion(choice);
        this.questions.add(newQuestion);
    }

    private Question makeQuestion(int choice) {
        switch (choice) {
            case 1:
                return this.buildTrueFalse();
            case 2:
                return this.buildMultiChoice();
        }

        return null;
    }

    private String promptForDescription() {
        boolean incomplete = true;
        String description = null;
        do {
            System.out.println("What is the question description?");
            try {
                input.nextLine(); // clear buffer ... ?
                description = input.nextLine();
                incomplete = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for description");
            }
        } while (incomplete);

        return description;
    }

    private HashMap<Integer, String> promptForChoices() {
        HashMap<Integer, String> choices = new HashMap<>();
        int choiceNumber = 1;

        System.out.println("Provide choices for the user to select frorm\n");
        do {
            System.out.println("Enter a value for choice " + choiceNumber);
            String choiceValue = this.input.nextLine();

            if (choices.values().contains(choiceValue)) {
                System.out.println("This answer already exists");
                continue;
            }
            
            choices.put(choiceNumber++, choiceValue);

            System.out.println("Enter 'done' to finish providing answer choices.\nOr press 'enter' to add anotger choicee");
            if (this.input.hasNextLine()) {
                String done = this.input.nextLine();
                if (done.equals("done")) break;
            }
        } while (true);

        return choices;
    }

    private int[] promptForAnswer() {
        int[] answer = new int[1];
        boolean incomplete = true;
        do {
            System.out.println("What is the correct answer?");
            try {
                int correctAnswer = input.nextInt();
                answer[0] = correctAnswer;
                incomplete = false;
            } catch (InputMismatchException e) {
                input.next(); // clear buffer
                System.out.println("Invalid input for answer");
            }
        } while (incomplete);

        return answer;
    }

    private int[] promptForAnswer(HashMap<Integer, String> choices) {
        int[] answer = new int[1];
        boolean incomplete = true;
        do {
            System.out.println("\nQuestion choices\n");
            for(Map.Entry<Integer, String> item : choices.entrySet()) {
                System.out.println(item.getKey() + ": " + item.getValue());
            }

            System.out.println("\nWhat is the correct answer?");
            try {
                int correctAnswer = input.nextInt();
                if (!choices.keySet().contains(correctAnswer)) {
                    System.out.println("Invalid answer (not in choices)");
                    continue;
                }
                answer[0] = correctAnswer;
                incomplete = false;
            } catch (InputMismatchException e) {
                input.next(); // clear buffer
                System.out.println("Invalid input for answer");
            }
        } while (incomplete);

        return answer;
    }

    // TODO: implement a 'promptForAnswers()' method that functions similarly to the one above
    // TODO: but is able to accept and store multiple answers
    // TODO: call this method in the MultiSelect subclass builder methods


    private TrueFalse buildTrueFalse() {
        String description = this.promptForDescription();
        int answer[] = this.promptForAnswer();

        return new TrueFalse(description, answer, this.input);
    }

    private MultiChoice buildMultiChoice() {
        String description = this.promptForDescription();
        HashMap<Integer, String> choices = this.promptForChoices();
        int[] answer = this.promptForAnswer(choices);

        return new MultiChoice(
            description,
            choices,
            answer,
            this.input
        );
    }

    // TODO: create a 'buildMultiSelect()' method
}
