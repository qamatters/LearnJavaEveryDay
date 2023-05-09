package org;

import java.util.Arrays;

public class array {
    public static void main(String[] args) {
        String[] labels = new String[2];
        for(int i =0; i<5; i++) {
            if(i ==0) {
                labels[0] = "abc:123";
            } else if(i==1) {
                labels[0] = "abc:124";
            } else {
                labels[0] = "abc:125";
            }

            System.out.println(labels[0]);
        }

    }
}
