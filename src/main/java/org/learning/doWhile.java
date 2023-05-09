package org.learning;
/*
Do while loops are a type of loop in Java that allows you to repeat a certain set of instructions until a certain condition has been met.
For example, let's say you wanted to print out the numbers 1 to 10 on the screen. You can use a do while loop to do this:
int counter = 1;
do {
  System.out.println(counter);
  counter++;
} while (counter <= 10);
This loop will print out the numbers 1 to 10 on the screen, with each number being printed out on a separate line.
 */

public class doWhile {
    public static void main(String[] args) {
        int counter = 1;
        do {
            System.out.println(counter);
            counter++;
        } while (counter <= 10);
    }
}
