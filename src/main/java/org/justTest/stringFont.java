package org;

import javax.swing.*;
import java.awt.*;

public class stringFont {
    public static void main(String[] args) {
        String str = "\u001B[1m" + "This is a bold string" + "\u001B[0m";
        String b =   "deepak";
        String latest = str + System.lineSeparator()+ b;
        System.out.print(latest);
    }
}
