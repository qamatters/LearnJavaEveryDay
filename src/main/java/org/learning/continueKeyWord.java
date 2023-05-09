package org.learning;
/*

The continue keyword is used to end the current iteration of a loop and continue with the next iteration.
For example, if we have a loop that prints the numbers 1 to 10, we can use the continue keyword to skip
any number divisible by 3 and only print the numbers not divisible by 3:

 */


public class continueKeyWord {
    public static void main(String[] args) {
                //loop from 1 to 10
                for (int i = 1; i <= 10; i++) {
                    //if the number is divisible by 3, skip this iteration
                    if (i % 3 == 0) {
                        continue;
                    }
                    System.out.println(i);
                }
            }
}
