package org.learning;

public class ternaryOperator {
    public static void main(String[] args) {
        int x = 5;
        int y ;
        if(x>5) {
            y =2;
        } else {
            y = 5;
        }
        int z = (x>5)?2:5;
        System.out.println(" y value from if else " + y + " z value from ternary " + z );
    }
}
