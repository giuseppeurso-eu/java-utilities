package eu.giuseppeurso.utilities.thirdpartyapi.xdocreport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import eu.giuseppeurso.utilities.thirdpartyapi.xdocreport.CustomerPojo.AddressPojo;



public class MAINExecutor {

	public static void main(String[] args) {
		
		AddressPojo addr1 = new CustomerPojo(). new AddressPojo("Iron Maiden", "666", "Leyton", "England");
		AddressPojo addr2 = new CustomerPojo(). new AddressPojo("Kiss", "1973", "New York City", "US");
		AddressPojo addr3 = new CustomerPojo(). new AddressPojo("Metallica", "1986", "Los Angeles", "US");
		List<AddressPojo> addrList = new ArrayList<CustomerPojo.AddressPojo>();
		addrList.add(addr1);
		addrList.add(addr2);
		addrList.add(addr3);
	
		CustomerPojo customer = new CustomerPojo("Lemmy", "Kilmister", "motorhead@metal.net", "666666", addrList);
		
		File templateOdt = new File("src/main/resources/xdocreport/customer-template.ftl.odt");
		File generatedPdf  = new File("target/generated-report.pdf");
		
		try {
			InputStream odtStream = FileUtils.openInputStream(templateOdt);
			OutputStream pdfStream = new FileOutputStream(generatedPdf);
			XdocreportGenerator.createPdf(odtStream, pdfStream, customer);
			System.out.println("Generated PDF file using XDocReport: "+generatedPdf.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

}
