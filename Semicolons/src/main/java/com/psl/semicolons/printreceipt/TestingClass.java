package com.psl.semicolons.printreceipt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class TestingClass {

	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {
		Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);

		// fonts for document
		Font body = FontFactory.getFont("Times-Roman", 8);
		Font companyName = FontFactory.getFont("Times-Roman", 15, Font.BOLD);

		// determining file name based on current time to maintain uniqueness
		Date date = new Date();
		String date1 = date.toString().replace(" ", "");
		date1 = date1.toString().replace(":", "");
		date1 = date1 + ".pdf";

		// opening document for writing
		PdfWriter.getInstance(document, new FileOutputStream(date1));
		document.open();

		// adding image to PDF
		Image img = Image.getInstance("C:\\Users\\harsh_jain\\git\\POS-58-Bluetooth-print\\Logo1.png");
		img.setAbsolutePosition(405f, 720f);
		document.add(img);

		// adding paragraph to PDF file
		Paragraph paragraph = new Paragraph("\nINVOICE", body);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		paragraph = new Paragraph("      Winnovators Pizza", companyName);
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph("             9A, Bharati Niwas Colony, Nal Stop" + "\n            Erandwane, Near Sharada Centre" + "\n            Pune - 411004" + "\n            Maharashtra" + "\n            Mobile: 9876543210" + "\n            Email: winnovators@persistent.co.in", body);
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		Rectangle rect1=new Rectangle(20,710,570,710);
		rect1.setBorder(Rectangle.BOX);
		rect1.setBorderWidth(1);
		rect1.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect1);

		// adding border to document
		Rectangle rect = new Rectangle(584, 832);
		rect.setLeft(10f);
		rect.setBottom(10f);
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(4);
		rect.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect);

		// closing document
		document.close();
	}
}
