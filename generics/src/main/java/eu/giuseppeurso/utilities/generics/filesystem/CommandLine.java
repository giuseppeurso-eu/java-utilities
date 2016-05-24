package eu.giuseppeurso.utilities.generics.filesystem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Some utilites to run CommnandLine operations.
 * @author Giuseppe Urso - www.giuseppeurso.eu
 *
 */
public class CommandLine {
	
	/**
	 * A method to run a command.
	 * @throws Exception
	 */
	public void exec(String command) throws Exception{
		try{
			String line;
		    OutputStream stdin = null;
		    InputStream stderr = null;
		    InputStream stdout = null;
		
		      // launch EXE and grab stdin/stdout and stderr
		      Process process = Runtime.getRuntime ().exec (command);
		      stdin = process.getOutputStream ();
		      stderr = process.getErrorStream ();
		      stdout = process.getInputStream ();
		  
		      // "write" the parms into stdin
		      line = "param1" + "\n";   
		      stdin.write(line.getBytes() );
		      stdin.flush();
		
		      line = "param2" + "\n";
		      stdin.write(line.getBytes() );
		      stdin.flush();
		
		      line = "param3" + "\n";
		      stdin.write(line.getBytes() );
		      stdin.flush();
		
		      stdin.close();
		      
		      // clean up if any output in stdout
		      BufferedReader brCleanUp = 
		        new BufferedReader (new InputStreamReader (stdout));
		      while ((line = brCleanUp.readLine ()) != null) {
		        System.out.println ("[Stdout] " + line);
		      }
		      brCleanUp.close();
		      
		      // clean up if any output in stderr
		      brCleanUp = 
		        new BufferedReader (new InputStreamReader (stderr));
		      while ((line = brCleanUp.readLine ()) != null) {
		        System.out.println ("[Stderr] " + line);
		      }
		      brCleanUp.close();
		      
		}catch(Exception e) {
			   e.printStackTrace();
			   throw e;
		}
	}

	/**
	 * Run a command with arguments.
	 * @param command
	 */
	public static void execWithArguments(String[] command){
		try {
			Process ps = Runtime.getRuntime().exec(command);
			System.out.print(loadStream(ps.getInputStream()));
			System.err.print(loadStream(ps.getErrorStream()));
			}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	/**
	 * Read an input-stream into a String
	 * @param in
	 * @return
	 * @throws IOException
	 */
	static String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		while( (ptr = in.read()) != -1 ) {
			buffer.append((char)ptr);
		}
		return buffer.toString();
	}
}
