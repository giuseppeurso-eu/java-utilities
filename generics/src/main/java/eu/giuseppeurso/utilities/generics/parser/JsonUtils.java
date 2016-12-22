package eu.giuseppeurso.utilities.generics.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.simple.JSONArray;

/**
 * Manipulating and parsing JSON files.
 * @author Giuseppe Urso - www.giuseppeurso.eu
 *
 */
public class JsonUtils {

	/**
	 * Scan directory which includes json files.
	 * 
	 * @param sourceDir
	 */
	public static void scanJsonDirectory(String sourceDir) {
		File folder = new File(sourceDir);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			System.out.println("File name: " + file.getAbsolutePath());
			byte[] jsonBytes;
			try {
				jsonBytes = FileUtils.readFileToByteArray(file);
				String jsonText = new String(jsonBytes, "UTF-8");
				org.json.JSONArray jsonA = new org.json.JSONArray(jsonText);
				System.out.println("JSON length: " + jsonA.length());
				System.out.println("First element: " + jsonA.get(0).toString());
				System.out.println("Last element: "
						+ jsonA.get(jsonA.length() - 1).toString());
				System.out.println("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Split JSON Array file. Example Source: [ {"key1":"value1","key2":"value2"},
	 * {"key1":"value3","key2":"value4"} ]
	 */
	public static void splitJsonArrayFile(String sourceFile, String destFile,
			int startIndex, int endIndex) {

		File file = new File(sourceFile);
		byte[] jsonBytes;
		try {
			jsonBytes = FileUtils.readFileToByteArray(file);
			String jsonText = new String(jsonBytes, "UTF-8");
			org.json.JSONArray jsonA = new org.json.JSONArray(jsonText);
			System.out.println("Source JSON Total Elements:" + jsonA.length()
					+ "\n");
			int parts = jsonA.length() / endIndex;

			if (jsonA.length() % endIndex == 0) {
				System.out.println("Numbers of part files: " + parts);
			} else {
				System.out.println("Numbers of part files: " + (parts + 1));
			}

			// System.out.println("Numbers of part files: "+parts);
			JSONArray jsonPart;
			int lastChunkIndex = 0;
			String currentF;
			for (int j = 0; j < parts; j++) {
				currentF = destFile + "-part" + j+".json";

				if (j == 0) {
					jsonPart = new JSONArray();

					for (int i = startIndex; i < endIndex; i++) {
						// System.out.println("Source Json element: "+jsonA.get(i));
						jsonPart.add(jsonA.get(i));
						// System.out.println("Dest JSON element:"+jsonPart.get(i)+"\n");
					}
					lastChunkIndex = endIndex;
					FileWriter dest = new FileWriter(currentF);
					dest.write(jsonPart.toJSONString());
					dest.flush();
					dest.close();
					System.out.println("Current Part: " + currentF);
					System.out
							.println("Dest Total Elements:" + jsonPart.size());
				} else {

					jsonPart = new JSONArray();
					for (int s = (endIndex * j); s < endIndex + (endIndex * j); s++) {
						// System.out.println("Source Json element: "+jsonA.get(i));
						jsonPart.add(jsonA.get(s));
						// System.out.println("Dest JSON element:"+jsonPart.get(i)+"\n");

					}

					lastChunkIndex = endIndex + (endIndex * j);
					FileWriter dest = new FileWriter(currentF);
					dest.write(jsonPart.toJSONString());
					dest.flush();
					dest.close();
					System.out.println("Current Part: " + currentF);
					System.out
							.println("Dest Total Elements:" + jsonPart.size());
				}

			}

			// The last part
			if (lastChunkIndex < jsonA.length()) {
				System.out.println("Last index: " + lastChunkIndex);
				jsonPart = new JSONArray();
				for (int s = lastChunkIndex; s < jsonA.length(); s++) {
					// System.out.println("Source Json element: "+jsonA.get(i));
					jsonPart.add(jsonA.get(s));
					// System.out.println("Dest JSON element:"+jsonPart.get(i)+"\n");
				}
				currentF = destFile + "-part" + parts+".json"; 
				FileWriter dest = new FileWriter(currentF);
				dest.write(jsonPart.toJSONString());
				dest.flush();
				dest.close();
				System.out.println("Last Part: " + currentF);
				System.out.println("Dest Total Elements:" + jsonPart.size());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get an element of a Json Array for a given index
	 * @param source
	 * @param index
	 */
	public static void getElementFromJsonArrayFile(String sourceFile, int index){
		
		File file = new File(sourceFile);
		byte[] jsonBytes;
		try {
			jsonBytes = FileUtils.readFileToByteArray(file);
			String jsonText = new String(jsonBytes, "UTF-8");
			org.json.JSONArray jsonA = new org.json.JSONArray(jsonText);
			System.out.println("Array size: "+jsonA.length());
			for (int i = 0; i < jsonA.length(); i++) {
				if(i==index){
					System.out.println("JSON Element "+index+" is: "+jsonA.get(i));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get an element of a Json Array from a given key
	 * @param source
	 * @param key
	 */
	public static void getValueFromJsonArrayFile(String sourceFile, String key){
		
		File file = new File(sourceFile);
		byte[] jsonBytes;
		try {
			jsonBytes = FileUtils.readFileToByteArray(file);
			String jsonText = new String(jsonBytes, "UTF-8");
			org.json.JSONArray jsonA = new org.json.JSONArray(jsonText);
			System.out.println("Array size: "+jsonA.length());
			for (int i = 0; i < jsonA.length(); i++) {
				org.json.JSONObject jsonObject =jsonA.getJSONObject(i);
				System.out.println("Current Value: "+jsonObject.get(key));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Search for an element into a Json Array from a given key and value
	 * @param source
	 * @param key
	 * @param value
	 */
	public static void searchValueInJsonArrayFile(String sourceFile, String key, String value){
		
		File file = new File(sourceFile);
		
		byte[] jsonBytes;
		try {
			System.out.println("File name: "+file.getCanonicalPath());
			jsonBytes = FileUtils.readFileToByteArray(file);
			String jsonText = new String(jsonBytes, "UTF-8");
			org.json.JSONArray jsonA = new org.json.JSONArray(jsonText);
			System.out.println("Array size: "+jsonA.length());
			for (int i = 0; i < jsonA.length(); i++) {
				org.json.JSONObject jsonObject =jsonA.getJSONObject(i);
				//System.out.println("Element: "+jsonObject.get(key));
				if(jsonObject.get(key).toString().equals(value)){
					System.out.println("Found Value at index: "+i);
					System.out.println("Element: "+jsonA.get(i));
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Count how many elements are in a JSON Array
	 * @param source
	 */
	public static void printSizeJsonArrayFile(String sourceFile){
		
		File file = new File(sourceFile);
		
		byte[] jsonBytes;
		try {
			System.out.println("File name: "+file.getCanonicalPath());
			jsonBytes = FileUtils.readFileToByteArray(file);
			String jsonText = new String(jsonBytes, "UTF-8");
			org.json.JSONArray jsonA = new org.json.JSONArray(jsonText);
			System.out.println("Array size: "+jsonA.length());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts string to JSONArray
	 * @param sourceString
	 * @return
	 */
	public static org.json.JSONArray stringArrayToJsonArray(String sourceString){
		if (sourceString!=null && !sourceString.equals("") && sourceString.substring(0,1).matches("\\[")) {
			org.json.JSONArray jArray = new org.json.JSONArray(sourceString);
			System.out.println(jArray);
			return jArray;
		} else {
			System.out.println("The source String must starts with '['.");
			return null;
		}	
	}

	/**
	 * Accessing members of items in a JSONArray
	 * @param sourceJArray
	 */
	public static void surfJsonArray(org.json.JSONArray sourceJArray, List<String> keysToPrint){
		System.out.println("Lenght of source JSON Array: "+sourceJArray.length());
		for (int i = 0; i < sourceJArray.length(); i++) {
		    JSONObject jsonobject = sourceJArray.getJSONObject(i);
		    //System.out.println(jsonobject);
		    for (String key : keysToPrint) {
				System.out.println(key+" : "+jsonobject.get(key));
			}
		}
	}
}
