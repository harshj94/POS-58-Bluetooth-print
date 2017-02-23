package com.psl.semicolons.printreceipt;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/printReceipt")
public class PrintReceiptResource {

	@Autowired
	private PrintReceiptService printReceiptService;

	private static final Logger logger = LoggerFactory.getLogger(PrintReceiptResource.class);

	@POST
	public Response printReceipt(InputDTO inputDTO) {

		logger.info("PrintReceiptResource: printReceipt: Started");

		try {
			logger.info("PrintReceiptResource: printReceipt: calling print receipt method");
			printReceiptService.printReceipt();
			logger.info("PrintReceiptResource: printReceipt: End");
			return Response.status(200).entity("Successfully printed").build();
		} catch (IOException e) {
			logger.error("PrintReceiptResource: printReceipt: " + e.getMessage(), e);
			return Response.status(200).entity(e).build();
		}
	}
}
