package com.iText.AlisonAnderson.PDF;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import static com.itextpdf.kernel.colors.ColorConstants.*;

import java.io.*;

public class PDFService {
    private static final String FILENAME_ONE = "upload-directory/test_csv_convert.csv";
    private static final String DESTINATION_ONE = "download-directory/test_csv_convert.pdf";
    private static final String FILENAME_TWO = "upload-directory/united_states.csv";
    private static final String DESTINATION_TWO = "download-directory/united_states.pdf";
    private static final String FILENAME_THREE = "upload-directory/test_csv_incomplete.csv";
    private static final String DESTINATION_THREE = "download-directory/test_csv_incomplete.pdf";

    public void createPDF(String path) throws IOException {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME_ONE));
            //BufferedReader reader = new BufferedReader(new FileReader(FILENAME_TWO));
           // BufferedReader reader = new BufferedReader(new FileReader(FILENAME_THREE));

            String line = reader.readLine();

            Table table = new Table(numOfColumns(line))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .useAllAvailableWidth();

            splitLine(table, line, true);

            while ((line = reader.readLine()) != null) {
                int counter = 0;
                splitLine(table, line, false);
                counter++;

                // if the table gets too large, flush to avoid memory exception
                if (counter % 50 == 0) {
                    table.flush();
                }
            }

            reader.close();

            PdfDocument pdf = new PdfDocument(new PdfWriter(DESTINATION_ONE));
            //PdfDocument pdf = new PdfDocument(new PdfWriter(DESTINATION_TWO));
           // PdfDocument pdf = new PdfDocument(new PdfWriter(DESTINATION_THREE));

            Document document = new Document(pdf, new PageSize(PageSize.A4).rotate());

            //event handler for header and footer
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new PageHeaderAndFooterEvent());

            document.add(table);
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void splitLine(Table table, String line, boolean isHeader) {
        try {
            // delimiter requires to be changed per CSV file

             String delimiters = "[-\\t,;.?!:@\\[\\](){}_*/]";
            //String delimiters = ";";
            // String delimiters = ",";

            int numOfColumns = table.getNumberOfColumns();

            String[] tempArray = line.split(delimiters);
                if (tempArray.length == numOfColumns) {
                    for (String s : tempArray) {
                        Cell cell = new Cell().add(new Paragraph(s));
                        if (isHeader) {
                            table.addHeaderCell(cell);
                        } else {
                            table.addCell(s);
                        }
                    }
                }else if (tempArray.length != numOfColumns) {
                        throw new Exception("Number of columns does not match");
                    }


            Color[] colors = {RED, ORANGE, YELLOW, GREEN, CYAN, MAGENTA};
            int colorIndex = 0;
            int counter = 1;

            //for loops to get cell index
            for (int i = 0; i < table.getNumberOfRows(); i++) {
                for (int j = 0; j < table.getNumberOfColumns(); j++) {
                    Cell cell = table.getCell(i, j);
                    table.getHeader().setBackgroundColor(LIGHT_GRAY);

                    Color res = WHITE;

                    //ternary to alternate row background colors between white, and cycling rainbow through the cells
                    res = counter % 2 == 0 ? colors[colorIndex] : WHITE;
                    cell.setBackgroundColor(res);

                    if (colorIndex < colors.length - 1) {
                        colorIndex++;
                    } else {
                        colorIndex = 0;
                    }
                }
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // table constructor requires int number of columns
    public int numOfColumns(String line) {
        try {
            String delimiters = "[-\\t,;.?!:@\\[\\](){}_*/]";
            //String delimiters = ";";
            String[] tempArray = line.split(delimiters);
            return tempArray.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}




