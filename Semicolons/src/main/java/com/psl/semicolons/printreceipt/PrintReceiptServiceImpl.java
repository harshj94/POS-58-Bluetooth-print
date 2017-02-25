package com.psl.semicolons.printreceipt;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.psl.semicolons.model.Items;
import com.psl.semicolons.model.Order;

public class PrintReceiptServiceImpl implements PrintReceiptService {

	private static final Logger logger = LoggerFactory.getLogger(PrintReceiptServiceImpl.class);

	String path = "C:\\Users\\harsh_jain\\git\\POS-58-Bluetooth-print\\Semicolons\\";

	public void printReceipt(Order order) throws IOException {

		logger.info("PrintReceiptServiceImpl: printReceipt: Started");

		FileOutputStream os = new FileOutputStream("COM5");
		PrintStream ps = new PrintStream(os);

		ps.println("--------WINNOVATORS PIZZA-------");
		ps.println("Persistent System, Nal Stop");
		ps.println("Erandwane, Near Sharada Centre");
		ps.println("Pune - 411004");
		ps.println("Email : feedback@winnovators.com");
		ps.println("------------------------------");

		ps.println("Order ID : " + order.getOrder_id());
		ps.println("Customer : " + order.getCust_id());
		ps.println("Contact: " + order.getContact_number());

		ps.println("------------------------------");

		ps.println("Q  Items                  Total");
		for (Items It : order.getItems()) {
			ps.printf("%-22s %8s %n", It.getQuantity() + " " + It.getItem_name(), It.getTotal_p());
		}

		ps.println("------------------------------");
		ps.printf("%-20s %10s %n", "Sub Total ", order.getTotal_amount());

		ps.printf("%-20s %10s %n", "Service Tax(14%) ", order.getTaxable_amount());

		ps.printf("%-20s %10s %n", "Grand Total ", order.getGrand_total());

		ps.println("------------------------------");

		ps.println("Thank you , Please Visit Again");
		ps.println("###### Team Winnovators ######");
		ps.println("\n");

		ps.print("\f");
		ps.close();

		logger.info("PrintReceiptServiceImpl: printReceipt: End");
	}

	public void sendMail(String attachmentName, Order order) throws MessagingException {

		logger.info("PrintReceiptServiceImpl: sendMail: Started");

		Properties properties = System.getProperties();
		String from = "sachin_jagtap@persistent.co.in";
		String pass = "Feb@2017";
		String to = order.getEmail_id();
		final String USERNAME = from;

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "mail.persistent.co.in");
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		// properties.put("mail.debug", "true");

		Session session = Session.getDefaultInstance(properties);

		Message mime = new MimeMessage(session);
		mime.setFrom(new InternetAddress(USERNAME));
		mime.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mime.setSubject("Winnovators Pizza Invoice for Order Id: " + order.getOrder_id());

		BodyPart messageBodyPart = new MimeBodyPart();

		// Now set the actual message
		messageBodyPart.setText("Hi " + order.getCust_id() + "\n\nPFA the invoice for your order.\n\nWe look forward to serve you again.\n\nThanks\nWinnovators Pizza");

		// Create a multi part message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();

		// give the file path
		String filename = path + attachmentName;
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName("Invoice.pdf");
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		mime.setContent(multipart);

		Transport transport = session.getTransport("smtp");
		transport.connect("mail.persistent.co.in", from, pass);
		transport.sendMessage(mime, mime.getAllRecipients());
		transport.close();

		logger.info("PrintReceiptServiceImpl: sendMail: End");
	}

	public String generatePDFInvoice(Order order) throws DocumentException, MalformedURLException, IOException {

		logger.info("PrintReceiptServiceImpl: generatePDFInvoice: Started");
		Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);

		// fonts for document
		Font body = FontFactory.getFont("Times-Roman", 8);
		Font companyName = FontFactory.getFont("Times-Roman", 15, Font.BOLD);
		Font orderName = FontFactory.getFont("Times-Roman", 9);
		Font totalAmount = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
		Font tableHeader = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
		Font userName = FontFactory.getFont("Times-Roman", 10);
		BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

		userName.setColor(BaseColor.DARK_GRAY);

		// determining file name based on current time to maintain uniqueness
		Date date = new Date();
		String dateFormatted = date.toString().replace(" ", "");
		dateFormatted = dateFormatted.toString().replace(":", "");
		dateFormatted = dateFormatted + ".pdf";

		// opening document for writing
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path+dateFormatted));
		document.open();

		PdfContentByte cb = pdfWriter.getDirectContent();

		// adding image to PDF
		Image img = Image.getInstance(path + "Logo1.png");
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

		Rectangle rect = new Rectangle(20, 710, 570, 710);
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(1);
		rect.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect);

		paragraph = new Paragraph("\n          Invoice Id: " + order.getOrder_id() + "\n          Invoice date: " + date.toString(), userName);
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		rect = new Rectangle(20, 665, 570, 665);
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(1);
		rect.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect);

		paragraph = new Paragraph("\n          Name: " + order.getCust_id() + "\n          Mobile: " + order.getContact_number() + "\n          Email: " + order.getEmail_id(), userName);
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		rect = new Rectangle(20, 608, 570, 608);
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(1);
		rect.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect);

		paragraph = new Paragraph("\nOrder Details\n\n", body);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(4);
		table.addCell(new Paragraph("Serial Number", tableHeader));
		table.addCell(new Paragraph("Name", tableHeader));
		table.addCell(new Paragraph("Quantity", tableHeader));
		table.addCell(new Paragraph("Amount", tableHeader));

		for (int tableCell = 0; tableCell < order.getItems().length; tableCell++) {
			table.addCell(new Paragraph(tableCell + 1 + "", orderName));
			table.addCell(new Paragraph(order.getItems()[tableCell].getItem_name(), orderName));
			table.addCell(new Paragraph(order.getItems()[tableCell].getQuantity(), orderName));
			table.addCell(new Paragraph(order.getItems()[tableCell].getTotal_p(), orderName));
		}

		PdfPCell cell = new PdfPCell(new Paragraph(" ", body));
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(" ", body));
		cell.setColspan(2);
		table.addCell(cell);

		table.addCell(new Paragraph("Sub-Total", body));
		table.addCell(new Paragraph(order.getTotal_amount(), body));

		cell = new PdfPCell(new Paragraph(" ", body));
		cell.setColspan(2);
		table.addCell(cell);

		table.addCell(new Paragraph("Service Tax @ 14%", body));
		table.addCell(new Paragraph(order.getTaxable_amount(), body));

		table.addCell(cell);

		table.addCell(new Paragraph("Total Amount", totalAmount));
		table.addCell(new Paragraph(order.getGrand_total(), totalAmount));
		document.add(table);

		cb.saveState();
		cb.beginText();
		cb.moveText(250, 360);
		cb.setFontAndSize(bf, 10);
		cb.showText("Total Amount: Rs. " + order.getGrand_total());
		cb.endText();
		cb.restoreState();

		// adding image to PDF
		img = Image.getInstance(path + "rsz_paytm.png");
		img.setAbsolutePosition(243f, 240f);
		document.add(img);

		cb.saveState();
		cb.beginText();
		cb.moveText(245, 225);
		cb.setFontAndSize(bf, 8);
		cb.showText("Scan this Paytm QR code to pay");
		cb.endText();
		cb.restoreState();

		cb.saveState();
		cb.beginText();
		cb.moveText(190, 185);
		cb.setFontAndSize(bf, 12);
		cb.showText("Thankyou. We look forward to see you again. :)");
		cb.endText();
		cb.restoreState();

		cb.saveState();
		cb.beginText();
		cb.moveText(30, 30);
		cb.setFontAndSize(bf, 8);
		cb.showText("Note: This is an electronically generated invoice and does not require a physical signature.");
		cb.endText();
		cb.restoreState();

		rect = new Rectangle(20, 100, 570, 100);
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(1);
		rect.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect);

		// adding border to document
		rect = new Rectangle(584, 832);
		rect.setLeft(10f);
		rect.setBottom(10f);
		rect.setBorder(Rectangle.BOX);
		rect.setBorderWidth(4);
		rect.setBorderColor(BaseColor.LIGHT_GRAY);
		document.add(rect);

		// closing document
		document.close();

		logger.info("PrintReceiptServiceImpl: generatePDFInvoice: End");
		return dateFormatted;
	}
	
	public String process_sms(String mob_no, String message) throws IOException {
		
		logger.info("PrintReceiptServiceImpl: process_sms: Start");

		message = URLEncoder.encode(message, "UTF-8");
		String working_key = "636n033l3549o14yp1ljdti3t81rk11v5";
		String sender_id = "SEDEMO";

		URL url = new URL("http://instantalerts.co/api/web/send?apikey=" + working_key + "&sender=" + sender_id + "&to="
				+ mob_no + "&message=" + message);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(true);
		con.getOutputStream();
		con.getInputStream();
		BufferedReader rd;
		String line;
		String result = "";
		rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((line = rd.readLine()) != null) {
			result += line;
		}
		rd.close();
		logger.info("PrintReceiptServiceImpl: process_sms: Result = "+result);
		logger.info("PrintReceiptServiceImpl: process_sms: End");

		return result;
	}

}
