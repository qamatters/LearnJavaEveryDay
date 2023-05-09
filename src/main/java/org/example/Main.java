package org.example;



import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> values = new ArrayList<>();
        values.add("1");
        values.add("");
        if(values.stream().noneMatch(s -> s.length() > 0)) {
            System.out.println("result is :" + true);
        }

    }
}