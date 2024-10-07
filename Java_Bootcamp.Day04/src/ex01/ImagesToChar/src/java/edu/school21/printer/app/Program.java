package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        checkArgs(args);

        try {
            Logic logic = new Logic(args[0].charAt(0), args[1].charAt(0), "/resources/it.bmp");
//            Logic logic = new Logic(args[0].charAt(0), args[1].charAt(0), "/Users/kandice/Desktop/JavaBootcamp/Java_Bootcamp.Day04-1/src/ex01/ImagesToChar/src/resources/it.bmp");
            logic.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: You should write three arguments: 1 - " +
                    "white symbol; 2 - black symbol; ");
            System.exit(-1);
        }
        if (args[0].length() != 1 || args[1].length() != 1) {
            System.out.println("First and second parameter must contain only one symbol");
            System.exit(-1);
        }
    }
}
