package org.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class abc {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("whoami");

        // Start the process and get the input stream.
        Process process = pb.start();
        InputStreamReader isr = new InputStreamReader(process.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        // Read the output of the whoami command.
        String line = br.readLine();
        System.out.println(line);
    }

}