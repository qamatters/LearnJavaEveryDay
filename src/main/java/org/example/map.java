package org.example;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.common.ValidatorListener;

import java.util.*;

public class map {
    public static void main(String[] args) {
        LinkedHashMap<String, LinkedList<String>> nameEmail = new LinkedHashMap<>();
        nameEmail.put("deepak", new LinkedList<>(Arrays.asList("deepak@abc.com", "abc.123@com")));
        nameEmail.put("sachin", new LinkedList<>(Arrays.asList("sachin@abc.com", "deepak.123@com")));

//        System.out.println(nameEmail.get("sachin").get(0));
//        System.out.println(nameEmail.get("sachin").get(1));
//        int value = 45;
//        String s = "-";
//        try {
//            if (Double.parseDouble(s) > 12) {
//                System.out.println("worked");
//            } else {
//                System.out.println("not worked");
//            }
//        } catch (Exception e)
//        {
//            System.out.println("value is :"+ s);
//        }


        LinkedList<String> values = new LinkedList<>();
        values.add("deepak.mathpal@accenture.com");
        values.add("abc@123.com");
        values.add("jpq@123.com");

        StringBuilder sb = new StringBuilder();

        for(String s: values) {
            sb = new StringBuilder(sb.append(s) + ",");
        }
        String emails = StringUtils.substringBeforeLast(String.valueOf(sb), ",");

        System.out.println(emails);


    }
}
