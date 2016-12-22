package eu.giuseppeurso.utilities.profiling;

import java.util.Calendar;

public class MAINexecutor {
	
	public static void main(String [] args) {
		
		String emptyString = "";  
	    String string = "ToBeOrNotToBeThatIsTheQuestion";
	    Integer zeroInt = 0;
	    Calendar cal = Calendar.getInstance();
        System.out.println("Object of type "+emptyString.getClass()+": "+ObjectSizeFetcher.getObjectSize(emptyString) +" [bytes]");
        System.out.println("Object of type "+string.getClass()+": "+ObjectSizeFetcher.getObjectSize(string)+" [bytes]");
        System.out.println("Object of type "+zeroInt.getClass()+": "+ObjectSizeFetcher.getObjectSize(zeroInt)+" [bytes]");
        System.out.println("Object of type "+cal.getClass()+": "+ObjectSizeFetcher.getObjectSize(cal)+" [bytes]");
    }

}
