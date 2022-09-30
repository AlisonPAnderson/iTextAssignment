package com.iText.AlisonAnderson.PDF;

import java.io.IOException;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;


public class PageHeaderAndFooterEvent implements IEventHandler {

    public void handleEvent(Event event) {

        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();

        PdfPage page = docEvent.getPage();
        int pageNumber = pdfDoc.getPageNumber(page);

        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

        //Place Header and footer
        try {
            pdfCanvas.beginText()
                    .setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 9)
                    .moveText(pageSize.getWidth() / 2 - 50, pageSize.getTop() - 20)
                    .showText("iText Candidate Assignment")
                    .moveText(5 , -pageSize.getTop() + 30)
                    .showText("Alison Anderson - Page " + String.valueOf(pageNumber))
                    .endText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfCanvas.release();
        }
    }