package org.learning;

import java.util.ArrayList;
import java.util.List;

public class removeAllNullFromAlist {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(1);
        list.add(null);
        System.out.println("list items are:" + list);
        while (list.remove(null));
        System.out.println("list items are:" + list);

    }
}
