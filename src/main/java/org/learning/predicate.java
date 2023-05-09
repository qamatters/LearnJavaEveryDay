package org.learning;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class predicate {
    public static void main(String[] args) {
        // This predicate is a rule that every string which is going to test against it should be greater than 5
        Predicate<String> greaterThen5Rule = s->s.length()>5;

        List<String> allStrings = Arrays.asList("Deepak", "Mathpal", "Ram");
        List<String> filteredStrings =  allStrings.stream()
                .filter(greaterThen5Rule)
                .collect(Collectors.toList());

        int index = Arrays.binarySearch(filteredStrings.toArray(), "Deepak");
        System.out.println("Index is :" + index);

        System.out.println("all string whose length is greater then 5 is :" + filteredStrings.get(0) + ", " +filteredStrings.get(1));

    }
}
