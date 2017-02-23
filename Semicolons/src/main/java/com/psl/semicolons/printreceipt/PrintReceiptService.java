package com.psl.semicolons.printreceipt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.mail.MessagingException;

import com.itextpdf.text.DocumentException;

public interface PrintReceiptService {
	
	/**
	 * method to print receipt
	 * @throws FileNotFoundException
	 * @throws IOException 
	 */
	public void printReceipt() throws FileNotFoundException, IOException;
	
	/**
	 * code to send email with attachment
	 * @throws MessagingException 
	 */
	public void sendMail() throws MessagingException;
	
	/**
	 * code to create pdf invoice
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void generatePDFInvoice() throws FileNotFoundException, DocumentException, MalformedURLException, IOException;

}
