package eu.giuseppeurso.utilities.generics.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.Comparator;

/**
 * Some utilities to manipulate files.
 * @author Giuseppe Urso - www.giuseppeurso.eu
 *
 */
public class FileOperations {

	/**
	 * Remove the file extension of the file entered 
	 * @param fileName
	 * @return
	 */
	public static String removeFileExtension(String fileName)
	{
		if(null != fileName && fileName.contains("."))
		{
			return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return null;
	}
	
	/**
	 * Get string occurrences in file
	 * @param filePath
	 * @param stringToSearch
	 */
	public static void searchStringInFile(String filePath, String stringToSearch){
		try {
            String stringSearch = stringToSearch;
            BufferedReader bf = new BufferedReader(new FileReader(filePath));
            int linecount = 0;
            String line;
            int occurrence = 0;

            while (( line = bf.readLine()) != null){
                linecount++;
                int indexfound = line.indexOf(stringSearch);
                if (indexfound > -1) {
                    System.out.println("Word was found at position " + indexfound + " on line " + linecount);
                    occurrence++;
                }
            }
            System.out.println("Total: "+occurrence);
            bf.close();
        }
        catch (IOException e) {
            System.out.println("IO Error Occurred: " + e.toString());
        }
	}
	
}
