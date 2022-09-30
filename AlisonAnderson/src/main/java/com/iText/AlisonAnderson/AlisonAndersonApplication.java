package com.iText.AlisonAnderson;

import com.iText.AlisonAnderson.PDF.PDFService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;

@SpringBootApplication
public class AlisonAndersonApplication {
	PDFService pdfService = new PDFService();
	String path = "";

	public static void main(String[] args) {

		SpringApplication.run(AlisonAndersonApplication.class, args);
		new AlisonAndersonApplication().makePdf();

	}

	private void makePdf() {
		try {
		pdfService.createPDF(path);

	} catch (IOException e) {
		e.printStackTrace();
		}

		}


}
