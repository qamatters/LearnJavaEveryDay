package org.example;

import java.io.File;
import java.util.LinkedList;

public class test {
    public static void main(String[] args) {
        String filesPath = "src/main/resources/CSV/";
        String otherPath = "src/main/resources/images/";
        LinkedList<String> csvfileNames = getFileNameFromFolder(filesPath);
        LinkedList<String> imageFileNames = getFileNameFromFolder(otherPath);

        if(csvfileNames.stream().allMatch(file->file.endsWith(".csv")) && imageFileNames.stream().allMatch(file->file.endsWith(".png"))) {
            System.out.println("all files are csv and png files");
        } else {
            System.out.println("all files dont have extention as csv");
        }
    }

    public static LinkedList<String> getFileNameFromFolder(String path) {
        File stageFolder = new File(path);
        File[] listOfStageFiles = stageFolder.listFiles();
        System.out.println("Files present inside folder : " + path);
        LinkedList<String> fileName = new LinkedList<>();
        for (int stageCount = 0; stageCount < listOfStageFiles.length; stageCount++) {
            if (listOfStageFiles[stageCount].isFile()) {
                System.out.println(stageCount + " : " + listOfStageFiles[stageCount].getName());
                fileName.add(listOfStageFiles[stageCount].getName());
            }
        }
        return fileName;
    }
}
