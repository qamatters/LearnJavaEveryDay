package org.apache.files;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class getFileExtention {
    public static void main(String[] args) throws IOException {
        String directory = "src/main/resources/";
        File dir = new File(directory);
        find(dir);
    }

    public static void find(File dir) {
        File[] files = dir.listFiles();
        for(File f: files) {
            System.out.println(Arrays.stream(Objects.requireNonNull(f.listFiles())).collect(Collectors.toList()));
        }

//        List<String> fileNames = Arrays.stream(files).flatMap(file -> Arrays.stream(file.list())).collect(Collectors.toList());
//        for(String s: fileNames) {
//            System.out.println(s);
//        }
    }
}