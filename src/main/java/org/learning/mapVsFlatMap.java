package org.learning;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class mapVsFlatMap {

/*
Java map takes a list of things and turns it into a list of something else. For example, you can take a list of numbers and turn it into a list of
their square roots. Java flatMap takes a list of things and turns it into one long list of all the things inside.
It's like taking a box of LEGOs and taking all the pieces out to make one big pile.
*/

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("one", "two", "three");
        List<String> flatMapped = strings.stream()
                .flatMap(string -> Arrays.stream(string.split("")))
                .collect(Collectors.toList());
        System.out.println(flatMapped);

    }

}
