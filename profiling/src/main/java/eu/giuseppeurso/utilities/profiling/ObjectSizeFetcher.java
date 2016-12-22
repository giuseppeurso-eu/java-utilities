package eu.giuseppeurso.utilities.profiling;

import java.lang.instrument.Instrumentation;

/**
 * Thanks to:
 *  
 * http://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object
 * http://marxsoftware.blogspot.it/2011/12/estimating-java-object-sizes-with.html
 *
 */
public class ObjectSizeFetcher {
	private static volatile Instrumentation instrumentation;

	/**
	 * Implementation of the overloaded premain method that is first invoked by 
     * the JVM during use of instrumentation. 
	 * @param args
	 * @param inst
	 */
    public static void premain(String args, Instrumentation inst) {
    	System.out.println("Pre-main...");  
        instrumentation = inst;
    }

    /**
     * Provide the memory size of the provided object (but not it's components).
     * @param o
     * @return
     */
    public static long getObjectSize(Object o) {
        if (instrumentation == null)  
        {  
        	String help = "\n HELP - Build source and run from terminal as follows: \n java -javaagent:<PATH>/profiling-1.0-SNAPSHOT.jar  -cp <PATH>/profiling-1.0-SNAPSHOT.jar eu.giuseppeurso.utilities.profiling.MAINexecutor \n";
           throw new IllegalStateException("Agent not initialized.\n"+help);  
        }  
        return instrumentation.getObjectSize(o);  
    }

}
