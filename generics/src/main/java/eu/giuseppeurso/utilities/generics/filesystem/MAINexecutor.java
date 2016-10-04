package eu.giuseppeurso.utilities.generics.filesystem;

import java.io.File;

public class MAINexecutor {
	
	public static void main(String argv[]) {
		
		// COMMANDLINE 
		//--------------------
//		try{
//			CommandLine ex = new CommandLine();
//			//String command = "dir";
//			String command = "C:\\Programmi\\Intelliant OCR\\ocr.exe \"C:\\Documents and Settings\\Administrator\\Desktop\\testpdf\\test.tif\"";
//			ex.exec(command);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		//
//		String [] cmd = {"mysql", "-uroot", "-proot", "-e","source /tmp/create_schema.sql"};
//		String [] cmd = {"mysql", "-uroot", "-proot", "-e", "delete from test_db.table1"};
//		String [] cmd = {"mysql", "-u", "root", "-p12345", "-e", "show databases"};
//		CommandLine.execWithArguments(cmd);
		
		// OS 
		//---------------------
//		String sn = Os.getHardDiskSerialNumber("/dev/sda");
//	    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, sn, "Serial Number of C:", javax.swing.JOptionPane.DEFAULT_OPTION);
		
		//
//		String cpuId = MiscUtils.getMotherboardSN();
//	    System.out.print("cpuid: "+cpuId);
//	    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, cpuId, "Motherboard serial number", javax.swing.JOptionPane.DEFAULT_OPTION);
		
		// FILEOPERATIONS
		//---------------------
//		String file = "src/test/resources/filesystem/test-03.txt";
//		String s  = "II";
//		FileOperations.searchStringInFile(file, s);
		
		//
//		String file = "src/test/resources/filesystem/test-03.txt";
//		System.out.println("File as string: "+FileOperations.getFileAsString(file));
		
		//
		String file = "src/test/resources/filesystem/test-01.pdf";
		File f = new File(file);
		System.out.println("BASE64 Encoded String: "+FileOperations.fileToBase64(f));
	}

}
