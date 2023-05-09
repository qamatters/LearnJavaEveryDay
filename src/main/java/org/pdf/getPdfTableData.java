package org.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDTableAttributeObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.fit.pdfdom.PDFDomTree;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;


public class getPdfTableData {
    public static void main(String[] args) throws IOException {
        PDDocument document = PDDocument.load(new File("src/main/resources/pdf/investorsGrowth.pdf"));
//        ObjectExtractor oe = new ObjectExtractor(document);
//        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm(); // Tabula algo.
//        Page page = oe.extract(1); // extract only the first page
//        List<Table> table = sea.extract(page);
//        for(Table tab: table) {
//            System.out.println("Table is :" +  tab);
//        }
        PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
        Rectangle2D rectangle2D = new Rectangle2D.Float(452,378,128,14);
        pdfTextStripperByArea.addRegion("region", rectangle2D);
        PDPage pdPage = document.getPage(1);
        pdfTextStripperByArea.extractRegions(pdPage);
        String textFromRegion = pdfTextStripperByArea.getTextForRegion("region");
        System.out.println("table data :" + textFromRegion);




//        Writer output = new PrintWriter("src/main/resources/pdf/pdf.html", "utf-8");
//        new PDFDomTree().writeText(doc, output);
//        output.close();
             //   PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

                //Retrieving the fields from the AcroForm
//                List<PDField> fields = acroForm.getFields();
//                System.out.println(fields);

                //Iterating over each field
//                for(PDField field : fields){
//                    System.out.println("fields are: " + field.getAcroForm());
                    //Getting the fully qualified name of the field
//                    String fullyQualifiedName = field.getFullyQualifiedName();
                    //Check if the field is a Table
//                    if(fullyQualifiedName.contains("Table")){
                        //Get the Table data
//                        System.out.println("Table:");
                        //Printing the Table data
//                        for (int r = 0; r < table.getRowCount(); r++) {
//                            for (int c = 0; c < table.getColumnCount(); c++) {
//                                System.out.print(table.getCell(r, c).getValueAsString());
//                                System.out.print("\t");
//                            }
//                            System.out.println();
//                        }
//                    }
                }
                //Closing the document
//                document.close();
//            }
        }
