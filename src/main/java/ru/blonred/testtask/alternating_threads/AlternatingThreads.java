package ru.blonred.testtask.alternating_threads;

import java.util.Scanner;

public class AlternatingThreads {
    private static final WriteAndRead WAR = new WriteAndRead();

    public static void main(String[] args) {
        int targetNumber = getInput();

        ThreadWriteAndRead tWaR1 = new ThreadWriteAndRead("Thread #1", targetNumber, WAR);
        ThreadWriteAndRead tWaR2 = new ThreadWriteAndRead("Thread #2", targetNumber, WAR);
        try {
            tWaR1.thread.join();
            tWaR2.thread.join();
        } catch (InterruptedException exc) {
            System.out.println("Main thread was interruption");
        }
    }

    private static int getInput(){
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println("Enter the number: ");
            input = scanner.nextInt();
        } while (!isValid(input));
        return input;
    }

    private static boolean isValid(int input) {
        if (input > 0 && input % 2 == 0) {
            return true;
        }
        System.out.println("The number must be even and greater than 0. Try again.");
        return false;
    }
}
