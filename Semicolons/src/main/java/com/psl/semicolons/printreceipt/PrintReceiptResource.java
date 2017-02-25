package com.psl.semicolons.printreceipt;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.psl.semicolons.model.Order;

@Component
@Path("/printReceipt")
public class PrintReceiptResource {

	@Autowired
	private PrintReceiptService printReceiptService;

	private static final Logger logger = LoggerFactory.getLogger(PrintReceiptResource.class);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response printReceipt(Order order) {

		logger.info("PrintReceiptResource: printReceipt: Started");

		try {
			logger.info("PrintReceiptResource: printReceipt: calling print receipt method");
			//printReceiptService.printReceipt(order);
			logger.info("PrintReceiptResource: printReceipt: calling generate invoice method");
			logger.info("PrintReceiptResource: printReceipt: calling send email method");
			printReceiptService.sendMail(printReceiptService.generatePDFInvoice(order), order);
			logger.info("PrintReceiptResource: printReceipt: calling send message");
			printReceiptService.process_sms(order.getContact_number(), "Test Message");
			logger.info("PrintReceiptResource: printReceipt: End");
			
			return Response.status(200).entity("{" + "\"status\":\"Successful Printing\"}").build();
		} catch (Exception e) {
			logger.error("PrintReceiptResource: printReceipt: " + e.getMessage(), e);
			return Response.status(404).entity("{" + "\"status\":\"Unsuccessful in Printing\"}").build();
		}
	}
	
	@GET
	public Response printReceipt() {

		logger.info("PrintReceiptResource: printReceipt: Started");

		logger.info("PrintReceiptResource: printReceipt: calling print receipt method");
		//printReceiptService.printReceipt();
		logger.info("PrintReceiptResource: printReceipt: End");
		return Response.status(200).entity("Successfully printed").build();
	}
}
