package eu.giuseppeurso.utilities.generics.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;


/**
 * Manipulating and parsing CSV files with Apache Comoons CSV.
 *  
 * https://commons.apache.org/proper/commons-csv/user-guide.html
 * 
 * @author Giuseppe Urso
 *
 */
public class CSVUtils{
	
	/**
	 * Reading the first line of a given csv file.
	 * @param csvFilePath
	 */
	public static String getCSVFirstLine(String csvFilePath){
		String header=null;
		File file = new File(csvFilePath);
		try {
			header = FileUtils.readLines(file, "UTF-8").get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return header;
	}
	
	
	/**
	 * Accessing column values by column name
	 * @param csvFilePath
	 * @param columnName
	 * @return
	 */
	public static List<String> getValuesByColumnName(String csvFilePath, String columnName){
		List<String> value = null;
		Reader in;
		try {
			in = new FileReader(csvFilePath);
			CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withIgnoreSurroundingSpaces();
			Iterable<CSVRecord> records = csvFormat.parse(in);
			value = new ArrayList<String>();
			for (CSVRecord record : records) {
				value.add(record.get(columnName));
			    //System.out.println(columnName+": "+value.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * Accessing column value 
	 * @param csvFilePath
	 * @param columnName
	 * @return
	 */
	public static String getValueByIndex(String csvFilePath, String columnName, int rowIndex){
		String value = null;
		Reader in;
		try {
			in = new FileReader(csvFilePath);
			CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withIgnoreSurroundingSpaces();
			CSVRecord record = csvFormat.parse(in).getRecords().get(rowIndex);
			value = record.get(columnName).trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * Retrieve the first value occurrence for a given pattern
	 * @param csvFilePath
	 * @param columnName
	 * @param pattern
	 * @return
	 */
	public static String getFirstMatchingValue(String csvFilePath, String columnName, String pattern){
		String value = null;
		Reader in;
		try {
			in = new FileReader(csvFilePath);
			CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withIgnoreSurroundingSpaces();
			Iterable<CSVRecord> records = csvFormat.parse(in);
			for (CSVRecord record : records) {
				String current = record.get(columnName);
				if(current!=null && current.contains(pattern)){
					//System.out.println("Pattern Matching! [Current: "+current.trim()+"] [Pattern: "+pattern+"]");
					value = current.trim();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * Get a list of values for a given pattern
	 * @param csvFilePath
	 * @param columnName
	 * @param pattern
	 * @return
	 */
	public static List<String> getMatchingValues(String csvFilePath, String columnName, String pattern){
		List<String> result = null;
		Reader in;
		try {
			in = new FileReader(csvFilePath);
			CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withIgnoreSurroundingSpaces();
			Iterable<CSVRecord> records = csvFormat.parse(in);
			result = new ArrayList<String>();
			for (CSVRecord record : records) {
				String current = record.get(columnName);
				if(current!=null && current.contains(pattern)){
					result.add(current.trim());
					//System.out.println("Pattern Matching! [Current: "+current.trim()+"] [Pattern: "+pattern+"]");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Compare values of two CSV files.
	 * 
	 * @param CSV1FilePath
	 * @param CSV1columnName
	 * @param CSV2FilePath
	 * @param CSV2columnName
	 * @return
	 */
	public static List<String> compareAndRetrieveValues(String CSV1FilePath, String CSV1columnName, String CSV2FilePath, String CSV2columnName ){
		List<String> result = null;
		CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withIgnoreSurroundingSpaces();
		Reader csv1;
		Reader csv2;
		try {
			csv1 = new FileReader(CSV1FilePath);
			Iterable<CSVRecord> recordsCsv1 = csvFormat.parse(csv1);
			result = new ArrayList<String>();
			for (CSVRecord recordCsv1 : recordsCsv1) {
				String currentCsv1 = recordCsv1.get(CSV1columnName);
				//System.out.println("[Current CSV1: "+currentCsv1.trim());
				csv2 = new FileReader(CSV2FilePath);
				Iterable<CSVRecord> recordsCsv2 = csvFormat.parse(csv2);
				for(CSVRecord recordCsv2 : recordsCsv2){
					String currentCsv2 = recordCsv2.get(CSV2columnName);
					//System.out.println("[Current CSV2: "+currentCsv2.trim());
					if(currentCsv1!=null && currentCsv1.contains(currentCsv2)){
						result.add(currentCsv2.trim());
						//System.out.println("MATCHED!!! - [Current CSV1: "+currentCsv1.trim()+"] [Current CSV2: "+currentCsv2+"]");
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Check if A CSV file includes a given pattern for a given column
	 * @param csvFilePath
	 * @param columnName
	 * @param pattern
	 * @return
	 */
	public static boolean containsValue(String csvFilePath, String columnName, String pattern){
		List<String> result = null;
		Reader in;
		try {
			in = new FileReader(csvFilePath);
			CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader().withIgnoreSurroundingSpaces();
			Iterable<CSVRecord> records = csvFormat.parse(in);
			result = new ArrayList<String>();
			for (CSVRecord record : records) {
				String current = record.get(columnName);
				if(current!=null && current.contains(pattern)){
					result.add(current.trim());
					//System.out.println("Pattern Matching! [Current: "+current.trim()+"] [Pattern: "+pattern+"]");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result!=null & result.size()>0){
			return true;
		}else{
			return false;
		}
	}
		
	/**
	 * Sorting lines of CSV file.
	 * @param inputFilePath
	 * @param outputFilePath
	 */
	public static void sortCSVLines(String inputFilePath, String outputFilePath) {
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(inputFilePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String inputLine;
			List<String> lineList = new ArrayList<String>();
			while ((inputLine = bufferedReader.readLine()) != null) {
				lineList.add(inputLine);
			}
			fileReader.close();

			Collections.sort(lineList);

			FileWriter fileWriter = new FileWriter(outputFilePath);
			PrintWriter out = new PrintWriter(fileWriter);
			for (String outputLine : lineList) {
				out.println(outputLine);
			}
			out.flush();
			out.close();
			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deleting duplicate lines in a CSV file
	 * @param inputFilePath
	 * @param outputFilePath
	 */
	public static void deleteDuplicateLines(String inputFilePath, String outputFilePath){
			File input = new File(inputFilePath);
			try {
				System.out.println("TOTAL Input lines: "+FileUtils.readLines(input).size());
				BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
			    Set<String> lines = new HashSet<String>(FileUtils.readLines(input).size());
			    String line;
			    while ((line = reader.readLine()) != null) {
			        lines.add(line);
			    }
			    reader.close();
			    
			    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
			    for (String unique : lines) {
			        writer.write(unique);
			        writer.newLine();
			    }
			    writer.close();
			    File output = new File(outputFilePath);
			    System.out.println("TOTAL Output lines: "+FileUtils.readLines(output).size());
			} catch (IOException e) {
				e.printStackTrace();
			}		
		    
		
	}
	
	/**
	 * Creating a new CSV with unique elements 
	 * @param CSV1FilePath
	 * @param outputFile
	 */
	public static void getCSVDistinct (String csvFilePath, String outputFile ){
		CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("Col_1","Col_2").withIgnoreSurroundingSpaces();
		Reader csv1;
		try {
			csv1 = new FileReader(csvFilePath);
			Iterable<CSVRecord> recordsCsv1 = csvFormat.parse(csv1);
			int totalOrigin = 0;
			List<String> distinct = new ArrayList<String>();
			for (CSVRecord recordCsv1 : recordsCsv1) {
				boolean contains=false;
				String current = recordCsv1.get(0).trim();
				//System.out.println("Current CSV element: "+current);
				if (distinct.size()==0) {
					distinct.add(current+","+recordCsv1.get(1).trim());
				} else {
					for (int i = 0; i < distinct.size(); i++) {
						//System.out.println("Comparing with: "+distinct.get(i));
						if(distinct.get(i).contains(current)){
							contains=true;
							//System.out.println("Current list element already present: "+distinct.get(i));
							break;
						}	
					}
					if (!contains) {
						distinct.add(current+","+recordCsv1.get(1).trim());
						contains = false;
					}
				}
			totalOrigin++;
			}
			File file= new File(outputFile);
			if(file.exists()){
				file.delete();
			}
		    FileUtils.writeLines(file, distinct);
	    	System.out.println("TOTAL DISTINCT: "+distinct.size());
			System.out.println("TOTAL ORIGIN: "+totalOrigin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}