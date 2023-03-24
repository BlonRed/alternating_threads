package ru.blonred.testtask.alternatingThreads;

import java.util.Scanner;

public class AlternatingThreads {

	public static void main(String[] args) {
		boolean run = true;
		do {
			System.out.println("Enter the number: ");
			int number = new Scanner(System.in).nextInt();
			if (number > 0 && number % 2 == 0) {
				WriteAndRead wr = new WriteAndRead();
				ThreadWR wt1 = new ThreadWR("Thread #1", number, wr);
				ThreadWR wt2 = new ThreadWR("Thread #2", number, wr);

				try {
					wt1.thrd.join();
					wt2.thrd.join();
					run = false;
				} catch (InterruptedException exc) {
					System.out.println("Main thread interruption");
				}
			} else System.out.println("Еhe number must be even and greater than 0. Try again.");
		} while (run);
	}
}
