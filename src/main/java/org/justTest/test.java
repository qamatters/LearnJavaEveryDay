package org;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
//        String s = "deepakmathpal";
//        StringBuilder sb = new StringBuilder(s);
//        s= String.valueOf(sb.reverse());
//        System.out.println(s);

        LinkedList<String> values = new LinkedList<>();
        values.add("deepak");
        values.add("mathpal");
        List<String> reverseVlues = values.stream().map(String->new StringBuilder(String).reverse().toString()).collect(Collectors.toList());
        System.out.println(reverseVlues);
        System.out.println(values);


    }
}
