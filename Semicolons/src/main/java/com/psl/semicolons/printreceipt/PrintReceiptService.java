package com.psl.semicolons.printreceipt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.mail.MessagingException;

import com.itextpdf.text.DocumentException;
import com.psl.semicolons.model.Order;

public interface PrintReceiptService {
	
	/**
	 * 
	 * @param order
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void printReceipt(Order order) throws FileNotFoundException, IOException;
	
	/**
	 * code to send email with attachment
	 * @throws MessagingException 
	 */
	public void sendMail(String attachmentName, Order order) throws MessagingException;
	
	/**
	 * code to create PDF invoice
	 * @return the path of the document
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public String generatePDFInvoice(Order order) throws FileNotFoundException, DocumentException, MalformedURLException, IOException;

}
