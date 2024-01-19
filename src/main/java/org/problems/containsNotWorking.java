package org.problems;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class containsNotWorking {
    public static void main(String[] args) {
        String directory = "src//main//resources//Excel//";
        File dir = new File(directory);
        File[] files = dir.listFiles();
        ArrayList<Object> fileNames = new ArrayList<>();
        for(File f: files) {
            fileNames.add(f.getName());
        }
//        fileNames.add("53062_WF_Proof_Summary_202310170845");
//        fileNames.add("aem_tagging.pptx");
//        fileNames.add("aem_tagging.pptx");
//        fileNames.add("aem_tagging.pptx");
        System.out.println("File names are : "+ fileNames);
        if(fileNames.contains("aem_tagging")) {
            System.out.println("file is present");
        } else {
            System.out.println("file is not present");
        }
    }
}
