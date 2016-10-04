package eu.giuseppeurso.utilities.generics.filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;



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
	
	/**
	 * Source: http://www.javapractices.com/Topic42.cjp
	 * @param aFile
	 * @return
	 */
	public static String loadTextFile(File aFile) {
	    //...checks on aFile are elided
	    StringBuffer contents = new StringBuffer();

	    //declared here only to make visible to finally clause
	    BufferedReader input = null;
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      input = new BufferedReader( new FileReader(aFile) );
	      String line = null; //not declared within while loop
	      /*
	      * readLine is a bit quirky :
	      * it returns the content of a line MINUS the newline.
	      * it returns null only for the END of the stream.
	      * it returns an empty String if two newlines appear in a row.
	      */
	      while (( line = input.readLine()) != null){
	        contents.append(line);
	        contents.append(System.getProperty("line.separator"));
	      }
	    }
	    catch (FileNotFoundException ex) {
	      ex.printStackTrace();
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    finally {
	      try {
	        if (input!= null) {
	          //flush and close both "input" and its underlying FileReader
	          input.close();
	        }
	      }
	      catch (IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	    return contents.toString();
	  }
	
	
	/**
	 * Source: http://www.javapractices.com/Topic42.cjp
	 * @param aFile
	 * @param aContents
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveTextFile(File aFile, String aContents) throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
//		if (!aFile.exists()) {
//			throw new FileNotFoundException ("File does not exist: " + aFile);
//		}
//		if (!aFile.isFile()) {
//			throw new IllegalArgumentException("Should not be a directory: " + aFile);
//		}
//		if (!aFile.canWrite()) {
//			throw new IllegalArgumentException("File cannot be written: " + aFile);
//		}

		// declared here only to make visible to finally clause; generic reference
		Writer output = null;
		try {
			// use buffering
			// FileWriter always assumes default encoding is OK!
			output = new BufferedWriter( new FileWriter(aFile) );
			output.write( aContents );
		}
		finally {
			// flush and close both "output" and its underlying FileWriter
			if (output != null) output.close();
		}
	}
	
	/**
	  * Get file as string. 
	  * 
	  * Source: http://www.javaworld.com/javaworld/javaqa/2003-08/01-qa-0808-property.html
	  * @param name
	  * @return
	  */
	public static String getFileAsString(String filePath) {
		StringBuffer file = new StringBuffer("");
		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			if ( in != null) {
				int c = -1;
				while ( (c = in.read()) != -1 ) {
					file.append((char) c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {in.close();} catch(Exception ignore) {}
		}
		return file.toString();
	}
	
	
	/**
	 * Convert file to a Base64 string
	 * @param file
	 * @return
	 */
	public static String fileToBase64(File file){
		String encodedContent="";
		try {
			InputStream is = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(is);
			encodedContent = Base64.encodeBase64String(bytes);
		} catch (FileNotFoundException e) {
			System.out.println("File Exception: "+e);
		} catch (IOException e) {
			System.out.println("IO Exception: "+e);
		}
		
		return encodedContent;
	}
	
	
}
