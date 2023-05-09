package org.Thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class parallelism {

    public static void main(String[] args) {

        // Create an ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ArrayList<Runnable> tasks = new ArrayList<>();
        tasks.add(() -> {
            System.out.println("Sum is: " + sum(1, 2));
        });
        tasks.add(()-> {
            System.out.println("Difference is :" +minus(1, 2));
        });
        tasks.add(()-> {
            System.out.println("Multiplication result is : " +multiply(2, 2));
        });

        for (Runnable task : tasks) {
            executor.execute(task);
        }
        executor.shutdown();
    }

    private static int sum(int i, int j) {
        return i + j;
    }

    private static int multiply(int i, int j) {
        return i * j;
    }

    private static int minus(int i, int j) {
        return i - j;
    }

}
