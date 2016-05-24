
package eu.giuseppeurso.utilities.generics.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Operating System functions.
 * 
 * http://www.tolstoy.com/samizdat/sysprops.html
 */
public class Os {

  /**
   * Get OS Name
   * @return
   */
  public static String getOsName() {
    return System.getProperty("os.name", "unknown");
  }
  
  /**
   * Get OS family 
   * @return
   */
  public static String platform() {
    String osname = System.getProperty("os.name", "generic").toLowerCase();
    if (osname.startsWith("windows")) {
      return "win32";
    }
    else if (osname.startsWith("linux")) {
      return "linux";
    }
    else if (osname.startsWith("sunos")) {
      return "solaris";
    }
    else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
      return "mac";
    }
    else return "generic";
  }

  /**
   * Check if OS is Windows
   * @return
   */
  public static boolean isWindows() {
    return (getOsName().toLowerCase().indexOf("windows") >= 0);
  }

  /**
   * Check if OS is Linux
   * @return
   */
  public static boolean isLinux() {
    return getOsName().toLowerCase().indexOf("linux") >= 0;
  }
  /**
   * Check if OS is Unix
   * @return
   */
  public static boolean isUnix() {
    final String os = getOsName().toLowerCase();

    // XXX: this obviously needs some more work to be "true" in general (see bottom of file)
    if ((os.indexOf("sunos") >= 0) || (os.indexOf("linux") >= 0)) { return true; }

    if (isMac() && (System.getProperty("os.version", "").startsWith("10."))) { return true; }

    return false;
  }
  
  /**
   * Check if OS is Mac
   * @return
   */
  public static boolean isMac() {
    final String os = getOsName().toLowerCase();
    return os.startsWith("mac") || os.startsWith("darwin");
  }
  
  /**
   * Check if OS is Solaris
   * @return
   */
  public static boolean isSolaris() {
    final String os = getOsName().toLowerCase();
    return os.indexOf("sunos") >= 0;
  }
  
  /**
   * A method to get Windows system root directory
   * @return
   */
  public static String findWindowsSystemRoot() {
    if (!isWindows()) { return null; }

    // commenting this out until we actually need it. I'm sick of seeing the
    // "use of deprecated API" warnings in our compiler output
    //
    // if (System.getProperty("java.version", "").startsWith("1.5.")) {
    // // System.getEnv(String name) is deprecated in java 1.2 through 1.4.
    // // Not only is it deprecated, it throws java.lang.Error upon invocation!
    // // It is has been un-deprecated in 1.5 though, so use it if we can
    // String root = System.getenv("SYSTEMROOT");
    // if (root != null) { return root; }
    // }

    // try to find it by looking at the file system
    final char begin = 'c';
    final char end = 'z';

    for (char drive = begin; drive < end; drive++) {
      File root = new File(drive + ":\\WINDOWS");
      if (root.exists() && root.isDirectory()) { return root.getAbsolutePath().toString(); }

      root = new File(drive + ":\\WINNT");
      if (root.exists() && root.isDirectory()) { return root.getAbsolutePath().toString(); }
    }
    return null;
  }
  
  
  /**
   * Get serial number of disk device in a Windows environment by using a VBS code snippet.
   * @param drive
   * @return
   */
  public static String getWinHardDiskSerialNumber(String drive) {
	  String result = "";
	    try {
	      File file = File.createTempFile("temp-hd-info",".vbs");
	      file.deleteOnExit();
	      FileWriter fw = new java.io.FileWriter(file);

	      String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
	                  +"Set colDrives = objFSO.Drives\n"
	                  +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
	                  +"Wscript.Echo objDrive.SerialNumber";  // see note
	      fw.write(vbs);
	      fw.close();
	      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	      BufferedReader input =
	        new BufferedReader
	          (new InputStreamReader(p.getInputStream()));
	      String line;
	      while ((line = input.readLine()) != null) {
	         result += line;
	      }
	      input.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    return result.trim();
	  }
  
  /**
   * Get serial number of the motherboard in a Windows environment by using a VBS code snippet.
   * @return
   */
  public static String getMotherboardSN() {
	  String result = "";
	    try {
	      File file = File.createTempFile("temp-mb-info",".vbs");
	      file.deleteOnExit();
	      FileWriter fw = new java.io.FileWriter(file);

	      String vbs =
	         "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
	        + "Set colItems = objWMIService.ExecQuery _ \n"
	        + "   (\"Select * from Win32_BaseBoard\") \n"
	        + "For Each objItem in colItems \n"
	        + "    Wscript.Echo objItem.SerialNumber \n"
	        + "    exit for  ' do the first cpu only! \n"
	        + "Next \n";

	      fw.write(vbs);
	      fw.close();
	      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	      BufferedReader input =
	        new BufferedReader
	          (new InputStreamReader(p.getInputStream()));
	      String line;
	      while ((line = input.readLine()) != null) {
	         result += line;
	      }
	      input.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    return result.trim();
	  }



}




