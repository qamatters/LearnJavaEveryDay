package org.excelToCsv;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class AdvancedExcelToCSVConverter {

    public static final int EXCEL_STYLE_ESCAPING = 0;
    public static final int UNIX_STYLE_ESCAPING = 1;
    private static final String DEFAULT_SEPARATOR = ",";
    private static final String CSV_FILE_EXTENSION = ".csv";
    private ArrayList<ArrayList<String>> csvData;
    private int maxRowWidth;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        boolean converted = true;
        try {
            AdvancedExcelToCSVConverter converter = new AdvancedExcelToCSVConverter();
            String strSource = "src/main/resources/Excel/LocationAndOffice.xlsx";
            String strDestination = "src/main/resources/CSV/";
            converter.convertExcelToCSV(strSource, strDestination,  EXCEL_STYLE_ESCAPING);
        } catch (Exception e) {
            System.out.println("Unexpected exception");
            e.printStackTrace();
            converted = false;
        }

        if (converted) {
            System.out.println("Conversion took " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds");
        }
    }

    public void convertExcelToCSV(String strSource, String strDestination, int formattingConvention)
            throws  IOException, IllegalArgumentException {

        File sourceFile = new File(strSource);
        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("The source Excel file cannot be found at " + sourceFile);
        }
        File destination = new File(strDestination);
        if (!destination.exists()) {
            throw new IllegalArgumentException(
                    "The destination directory " + destination + " for the " + "converted CSV file(s) does not exist.");
        }
        if (!destination.isDirectory()) {
            throw new IllegalArgumentException(
                    "The destination " + destination + " for the CSV file is not a directory/folder.");
        }
        if (formattingConvention != AdvancedExcelToCSVConverter.EXCEL_STYLE_ESCAPING && formattingConvention != AdvancedExcelToCSVConverter.UNIX_STYLE_ESCAPING) {
            throw new IllegalArgumentException("The value passed to the "
                    + "formattingConvention parameter is out of range: " + formattingConvention + ", expecting one of "
                    + AdvancedExcelToCSVConverter.EXCEL_STYLE_ESCAPING + " or "
                    + AdvancedExcelToCSVConverter.UNIX_STYLE_ESCAPING);
        }
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(sourceFile);
            System.out.println("Opening workbook [" + sourceFile.getName() + "]");
            workbook = WorkbookFactory.create(fis);
            int totalSheets = workbook.getNumberOfSheets();
            System.out.println("Total sheets : " + totalSheets);
            for(int i =0 ; i <totalSheets; i++) {
                String sheetName = workbook.getSheetName(i);
                int sheetIndex = i+1;
                System.out.println( sheetIndex + " sheet name is : " + sheetName);
                convertToCSV(workbook, i);
                String fileNameWithoutExtension = FileNameUtils.getBaseName(sourceFile.getName());
                String destinationFilename =fileNameWithoutExtension + "_" + sheetName;
                System.out.println(" destination file name is :" + destinationFilename);;
                destinationFilename = destinationFilename  + CSV_FILE_EXTENSION;
                System.out.println("destination file name is with csv " + destinationFilename);
                saveCSVFile(new File(destination, destinationFilename), formattingConvention);
            }

        } catch (Exception e) {
            System.out.println("Unexpected exception");
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    private String escapeEmbeddedCharacters(String field, int formattingConvention) {

        StringBuilder buffer;
        if (AdvancedExcelToCSVConverter.EXCEL_STYLE_ESCAPING == formattingConvention) {
            if (field.contains("\"")) {
                buffer = new StringBuilder(field.replace("\"", "\\\"\\\""));
                buffer.insert(0, "\"");
                buffer.append("\"");
            } else {
                buffer = new StringBuilder(field);
                if ((buffer.indexOf(DEFAULT_SEPARATOR)) > -1 || (buffer.indexOf("\n")) > -1) {
                    buffer.insert(0, "\"");
                    buffer.append("\"");
                }
            }
            return buffer.toString().trim();
        }
        else {
            if (field.contains(DEFAULT_SEPARATOR)) {
                field = field.replaceAll(DEFAULT_SEPARATOR, ("\\\\" + DEFAULT_SEPARATOR));
            }
            if (field.contains("\n")) {
                field = field.replace("\n", "\\\\\n");
            }
            return field;
        }
    }

    private void convertToCSV(Workbook workbook, int sheetNo) {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        DataFormatter formatter = new DataFormatter(true);
        this.csvData = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(sheetNo);
        System.out.println("Converting " + sheet.getSheetName() + " files contents to CSV format.");
            if (sheet.getPhysicalNumberOfRows() > 0) {
                int lastRowNum = sheet.getLastRowNum();
                System.out.println("last row num is:" + lastRowNum);
                for (int j = 0; j <= lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    ArrayList<String> csvLine = new ArrayList<>();
                    if(row == null) {
                        csvLine.add("");
                    } else {
                        int lastCellNum = row.getLastCellNum();
                        for (int k = 0; k <= lastCellNum; k++) {
                            Cell cell = row.getCell(k);
                            if (cell == null) {
                                csvLine.add("");
                            } else {
                                if (cell.getCellType() != CellType.FORMULA) {
                                    csvLine.add(formatter.formatCellValue(cell));
                                } else {
                                    System.out.println("value is :" + formatter.formatCellValue(cell, evaluator));
                                    csvLine.add(formatter.formatCellValue(cell, evaluator));
                                }
                            }
                        }
                        if (lastCellNum > this.maxRowWidth) {
                            this.maxRowWidth = lastCellNum;
                        }
                    }
                    this.csvData.add(csvLine);
                }
            }
    }

    private void saveCSVFile(File file, int formattingConvention) throws IOException {
        ArrayList<String> line;
        StringBuilder buffer;
        String csvLineElement;

        try (BufferedWriter bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.ISO_8859_1)) {

            System.out.println("Saving the CSV file [" + file.getName() + "]");
            for (int i = 0; i < this.csvData.size(); i++) {
                buffer = new StringBuilder();
                line = this.csvData.get(i);
                for (int j = 0; j < this.maxRowWidth; j++) {
                    if (line.size() > j) {
                        csvLineElement = line.get(j);
                        if (csvLineElement != null) {
                            buffer.append(escapeEmbeddedCharacters(csvLineElement, formattingConvention));
                        }
                    }
                    if (j < (this.maxRowWidth - 1)) {
                        buffer.append(DEFAULT_SEPARATOR);
                    }
                }

                bw.write(buffer.toString().trim());
                if (i < (this.csvData.size() - 1)) {
                    bw.newLine();
                }
            }
        }
    }

}
