package eu.giuseppeurso.utilities.generics.parser;


import org.json.JSONObject;

import junit.framework.TestCase;


/**
 * Unit test class.
 * 
 * @author Giuseppe Urso
 * 
 */
public class JsonUtilsTest extends TestCase {

	private String resourceDir = "";
	private String targetDir = "";
	private String testFile1= "";
	
	
	/**
	 * Setup test cases
	 */
	public void setUp() {
		resourceDir = "src/test/resources/parser";
		targetDir = "target";
		testFile1 = resourceDir + "/test-01.csv";
	}   
	
	/**
	 * Test case
	 */
	public void testStringObjectToJsonObject() {
		boolean assertActual = false;
		String input = "{\"key1\":\"black\",\"key2\":\"red\"}";
		JSONObject jObj = JsonUtils.stringObjectToJsonObject(input);
		if (jObj!=null && jObj.get("key1")=="black") {
			System.out.println("TEST CASE: " + this.getName() + " -> Output: "+jObj);
			assertActual = true;
		}
		assertEquals("TEST FAILURE", true, assertActual);
	}
	
		
}