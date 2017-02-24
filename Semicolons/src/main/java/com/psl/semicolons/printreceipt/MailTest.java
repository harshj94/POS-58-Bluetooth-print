package com.psl.semicolons.printreceipt;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailTest {

	public static void main(String[] args) throws Exception{

		
		
		Properties properties = System.getProperties();
		String from = "sachin_jagtap@persistent.co.in";
		String pass = "Feb@2017";
		String to = "harsh_jain@persistent.com";
		final String USERNAME = from;

		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "mail.persistent.co.in");
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

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

//		 // Part two is attachment
//		 messageBodyPart = new MimeBodyPart();
//		
//		 // give the file path
//		 String filename = "C:\\Users\\harsh\\Desktop\\demo.png";
//		 DataSource source = new FileDataSource(filename);
//		 messageBodyPart.setDataHandler(new DataHandler(source));
//		 messageBodyPart.setFileName(filename);
//		 multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		mime.setContent(multipart);

		Transport transport = session.getTransport("smtp");
		transport.connect("mail.persistent.co.in", from, pass);
		
		transport.sendMessage(mime, mime.getAllRecipients());
		transport.close();
	}
}
