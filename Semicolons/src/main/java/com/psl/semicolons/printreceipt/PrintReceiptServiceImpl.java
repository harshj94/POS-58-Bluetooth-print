package com.psl.semicolons.printreceipt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Properties;

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

import com.itextpdf.text.DocumentException;
import com.psl.semicolons.model.Order;

public class PrintReceiptServiceImpl implements PrintReceiptService {

	private static final Logger logger = LoggerFactory.getLogger(PrintReceiptServiceImpl.class);

	public void printReceipt(Order order) throws IOException {

		logger.info("PrintReceiptServiceImpl: printReceipt: Started");

		// for printing the text
		FileOutputStream os = new FileOutputStream("COM9");
		PrintStream ps = new PrintStream(os);
		ps.println("Receipt Sucessfully Printed");
		ps.println("order id:"+order.getOrder_id());
		
		ps.print("\f");
		ps.close();

		// for printing image using CMD command
//		Runtime rt = Runtime.getRuntime();
//		rt.exec(new String[] { "cmd.exe", "/c", "start" });
//		rt.exec("mspaint /p C:\\Users\\harsh\\Desktop\\demo.png");
//		rt.exec("taskkill /f /im cmd.exe");

		logger.info("PrintReceiptServiceImpl: printReceipt: End");
	}

	public void sendMail() throws MessagingException {

		logger.info("PrintReceiptServiceImpl: sendMail: Started");

		Properties properties = System.getProperties();
		String from = "harshj94@outlook.com";
		String pass = "Hkj@24031994";
		String to = "harsh_jain@persistent.co.in";
		final String USERNAME = from;

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.live.com");
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		// properties.put("mail.debug", "true");

		Session session = Session.getDefaultInstance(properties);

		Message mime = new MimeMessage(session);
		mime.setFrom(new InternetAddress(USERNAME));
		mime.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mime.setSubject("subject");

		BodyPart messageBodyPart = new MimeBodyPart();

		// Now set the actual message
		messageBodyPart.setText("This is message body");

		// Create a multi part message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

//		// Part two is attachment
//		messageBodyPart = new MimeBodyPart();
//
//		// give the file path
//		String filename = "C:\\Users\\harsh\\Desktop\\demo.png";
//		DataSource source = new FileDataSource(filename);
//		messageBodyPart.setDataHandler(new DataHandler(source));
//		messageBodyPart.setFileName(filename);
//		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		mime.setContent(multipart);

		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.live.com", from, pass);
		transport.sendMessage(mime, mime.getAllRecipients());
		transport.close();

		logger.info("PrintReceiptServiceImpl: sendMail: End");
	}

	public void generatePDFInvoice() throws DocumentException, MalformedURLException, IOException {

		logger.info("PrintReceiptServiceImpl: generatePDFInvoice: Started");

//		Document document = new Document();
//		PdfWriter.getInstance(document, new FileOutputStream("sample1.pdf"));
//		document.open();
//		Image img = Image.getInstance("C:\\Users\\harsh\\Desktop\\demo.png");
//		document.add(new Paragraph("Sample 1: This is simple image demo."));
//		document.add(img);
//		document.close();

		logger.info("PrintReceiptServiceImpl: generatePDFInvoice: End");

	}

}
