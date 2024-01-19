package org.problems;

import org.bouncycastle.util.Strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class issues {

    public static void main(String[] args) {
        List<String> countries = Arrays.asList("India", "Canada", "Boston", "Mexico");
//        LinkedList<String> countries = new LinkedList<>(countries1);
        countries.replaceAll(Strings::toUpperCase);
        Collections.sort(countries);
        countries.remove(2);
        System.out.println(countries);
    }
}
