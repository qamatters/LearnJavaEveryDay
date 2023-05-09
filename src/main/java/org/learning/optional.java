package org.learning;


import java.util.Optional;

public class optional {
    public static void main(String[] args) {
        String myName = null;
        for(int i =0 ; i<3 ; i++) {
            if(i ==1) {
                myName = "deepak";
            }
            Optional<String> myNameValue = Optional.ofNullable(myName);
            if(myNameValue.isPresent()) {
                System.out.println("non null value "+ myNameValue);
            } else {
                System.out.println(" null value :"+ myNameValue);
            }
        }

    }
}
