package org.problems;

import java.util.ArrayList;

public class containsNotWorkingForObjects {
    public static void main(String[] args) {
        String fileToBeFound = "sampleData";
        ArrayList<Object> fileNames = new ArrayList<>();
        fileNames.add("LocationAndOffice.xlsx");
        fileNames.add("sampleData.xlsx");
        System.out.println("File names are : "+ fileNames);
        if(fileNames.contains(fileToBeFound)) {
            System.out.println("file: " + fileToBeFound + "  is present");
        } else {
            System.out.println("file: " + fileToBeFound + "  is not present");
        }

        // solutions
//        System.out.println("File found " + fileNames.stream().anyMatch(s->s.toString().contains(fileToBeFound)));
    }
}
