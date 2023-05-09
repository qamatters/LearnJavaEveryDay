package org.learning;

/*

The break keyword is used to immediately exit out of a loop. It can be used within any type of loop, such as for, while, and do-while loops.
For example, consider the following for loop that prints out the numbers 0 through 9:

for(int i = 0; i < 10; i++) {
    System.out.println(i);
}
If we wanted to exit out of the loop after printing the number 5, we could do so using the break keyword like so:
for(int i = 0; i < 10; i++) {
    System.out.println(i);
    if(i == 5)
        break;
}
This loop prints out the numbers 0 through 5 and then exits out of the loop since the break keyword was encountered.

 */

public class breakKeyWord {
    public static void main(String[] args) {
        //loop from 1 to 10

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if (i == 5)
                break;
        }
    }
}
