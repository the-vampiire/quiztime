package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	    Scanner input  = new Scanner(System.in);
	    Quiz.demo(input); // demo version

        // instantiation version
//	    Quiz quiz = new Quiz("Test Quiz", input);
//	    quiz.start(false); // "restart" boolean parameter (false is a fresh start)
    }
}
