package com.psl.semicolons.printreceipt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;

public class TestingClass {
	
	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {
//		Document document = new Document();
//		PdfWriter.getInstance(document, new FileOutputStream("sample1.pdf"));
//		document.open();
//		Image img = Image.getInstance("C:\\Users\\harsh\\Desktop\\demo.png");
//		document.add(new Paragraph("Sample 1: This is simple image demo."));
//		document.add(img);
//		document.close();
		
		FileOutputStream os = new FileOutputStream("COM4");
		PrintStream ps = new PrintStream(os);
		ps.println("HAI APNA DETAILS harsh 1");
		ps.println("HAI APNA DETAILS harsh 2");
		ps.println("HAI APNA DETAILS harsh 3");
		ps.println("HAI APNA DETAILS harsh 4");
		ps.println("HAI APNA DETAILS harsh 5\n\n");

		ps.print("\f");
		ps.close();
	}
}
