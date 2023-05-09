package org.learning;

import java.util.ArrayList;
import java.util.List;

public class removeElementsWhileIterating {
    public static void main(String[] args) {
        List<String> values = new ArrayList<>();
        values.add("A");
        values.add("b-");
        values.add("c");
        values.add("D-");
        values.removeIf(value -> value.endsWith("-"));
        System.out.println("Values are: " + values);

        List<Integer> numbers = new ArrayList<>();

        // Adding elements to the ArrayList
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        numbers.removeIf(number -> number%2==0);
        System.out.println("numbers are : " + numbers);

    }
}
