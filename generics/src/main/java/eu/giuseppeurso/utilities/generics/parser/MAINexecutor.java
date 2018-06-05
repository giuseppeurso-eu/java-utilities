package eu.giuseppeurso.utilities.generics.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MAINexecutor {

	public static void main(String[] args) throws IOException {
		
		String source = "src/test/resources/parser/test-06.json";
//		String source = "src/test/resources/parser/test-01.xml";
		String dest = "target";
		
//		JsonUtils.splitJsonArrayFile(source, dest, 2);
				
//		JsonUtils.getElementFromJsonArrayFile(source, 1);
				
//		JsonUtils.printValueFromJsonArrayFile(source, "descrizione");
		
//		JsonUtils.searchValueInJsonArrayFile(source, "id","8898");
				
//		JsonUtils.countSizeJsonArrayFile(source);
	
//		JSONArray jarr = JsonUtils.stringArrayToJsonArray(JsonUtils.jsonFileToString(source));
//		List<String> keys = new ArrayList<String>();
//		keys.add("id");
//		keys.add("nome");
//		JsonUtils.surfJsonArray(jarr, keys);
		
//		String xmlString = "<?xml version='1.0' encoding='UTF-8'?><testTag/>";
//		XmlUtils.stripXmlBOM(xmlString,"UTF-16");

//		String xmlSource = "src/test/resources/parser/test-01.xml";
//		File file = new File(xmlSource);
//		XmlUtils.detectEncoding(file);
//		
//		InputStream is = new FileInputStream(source);
//		String destFile = dest+"/"+"test.noBOM";
//		File file = new File(destFile);
//		FileUtils.copyInputStreamToFile(XmlUtils.checkForUtf8BOMAndDiscardIfAny(is), file);
				
		FileReader json = new FileReader(source);
		JSONObject jsonObj = JsonUtils.fileReaderToJsonObject(json);
		System.out.println("JSON: "+jsonObj.toString());
	}
	
	
	
}
