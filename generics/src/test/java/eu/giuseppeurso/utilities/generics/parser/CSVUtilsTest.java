package eu.giuseppeurso.utilities.generics.parser;

import java.util.List;
import eu.giuseppeurso.utilities.generics.parser.CSVUtils;
import junit.framework.TestCase;


/**
 * Unit test class.
 * 
 * @author Giuseppe Urso
 * 
 */
public class CSVUtilsTest extends TestCase {

	private String resourceDir = "";
	private String targetDir = "";
	private String testFile1= "";
	private String testFile2= "";
	private String testFile3= "";
	
	/**
	 * Setup test cases
	 */
	public void setUp() {
		resourceDir = "src/test/resources/parser";
		targetDir = "target";
		testFile1 = resourceDir + "/test-01.csv";
		testFile2 = resourceDir + "/test-02.csv";
		testFile3 = resourceDir + "/test-03.csv";
	}   
	
	/**
	 * Test case
	 */
	public void testGetCSVFirstLine() {
		boolean assertActual = false;
		String header = CSVUtils.getCSVFirstLine(testFile1);
		
		if (header!=null && !header.equals("")) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+header);
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testGetValuesByColumnName() {
		boolean assertActual = false;
		List<String> values= CSVUtils.getValuesByColumnName(testFile1, "ID");
		
		if (values!=null) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+values.get(0));
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void test1_GetValueByIndex() {
		boolean assertActual = false;
		String value= CSVUtils.getValueByIndex(testFile1, "ID", 2);
		
		if (value!=null && !value.equals("")) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+value);
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void test2_GetValueByIndex() {
		boolean assertActual = false;
		String value= CSVUtils.getValueByIndex(testFile2, "type", 0);
		
		if (value!=null && !value.equals("")) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: '"+value+"'");
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testGetFirstMatchingValue() {
		boolean assertActual = false;
		String pattern = "_wergferge_werwer_w2eq.xlsx";
		String result = CSVUtils.getFirstMatchingValue(testFile2, "uid", pattern); 
		if (result !=null && result.contains(pattern)){
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+result);
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testGetMatchingValues() {
		boolean assertActual = false;
		String pattern = "_Crewtrwerwe.jpg";
		List<String> result = CSVUtils.getMatchingValues(testFile2, "uid", pattern); 
		if (result !=null && result.size()>0){
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+result.size()+ " results found! ");
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testCompareAndRetrieveValues() {
		boolean assertActual = false;
		
		List<String> result = CSVUtils.compareAndRetrieveValues(testFile2, "uid", testFile1, "CONTENT_URL"); 
		if (result.size()==0){
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+result.size()+ " results found! ");
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testSortCSVLines() {
		boolean assertActual = false;
		String input = testFile3;
		String output= targetDir + "/sorted.csv";
		CSVUtils.sortCSVLines(input, output);
		java.io.File f = new java.io.File(output);
		
		if (f.isFile() && f.length()>0) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: File size "+f.length()+" bytes ");
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testDeleteDuplicateLines() {
		boolean assertActual = false;
		String input = testFile3;
		String output= targetDir + "/unique-lines.csv";
		CSVUtils.deleteDuplicateLines(input, output);
		java.io.File f = new java.io.File(output);
		
		if (f.isFile() && f.length()>0) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: File size "+f.length()+" bytes ");
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
	/**
	 * Test case
	 */
	public void testGetCSVDistinct() {
		boolean assertActual = false;
		String input = testFile3;
		String output= targetDir + "/distinct.csv";
		CSVUtils.getCSVDistinct(input, output);
		
		java.io.File f = new java.io.File(output);
		
		if (f.isFile() && f.length()>0) {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: File size "+f.length()+" bytes ");
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
		
	}
	
}