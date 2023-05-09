package org.Thread;

import java.util.concurrent.*;

public class lanbdaExpressioninThread {
    public static void main(String[] args) {
        System.out.println("Main thread starting");
        // Define a Callable using a lambda expression
        Callable<String> myCallable = () -> {
            System.out.println("Running in separate thread");
            return "Hello from separate thread";
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submit the Callable to the executor and get a Future
        Future<String> future = executor.submit(myCallable);
        try {
            // Wait for the task to complete and retrieve the result
            String result = future.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        executor.shutdown();
        System.out.println("Main thread ending");
    }
}
