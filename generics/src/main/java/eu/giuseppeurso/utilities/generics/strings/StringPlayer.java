package eu.giuseppeurso.utilities.generics.strings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;


public class StringPlayer {
	
	
	/**
	 * Split string providing start and end index.
	 * @param input
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[] splitByDelimiter (String input,String delimiter){
		if (input!=null && !input.equals("")) {
			String[] splitted = input.split(delimiter);
			for (int i = 0; i < splitted.length; i++) {
				System.out.println("String #"+i+": "+splitted[i]);
			}
			
		 return splitted;
		}else {
			return null;
		}
	}
	
	/**
	 * Generate random UUID
	 * @return
	 * @see http://docs.oracle.com/javase/7/docs/api/java/util/UUID.html#randomUUID%28%29
	 */
	public static String getRandomUUID() {
		UUID id = UUID.randomUUID();
		System.out.println(id);
		return id.toString();
	}
	
	/**
	 * @param <T>
	 *
	 */
	public static Map<String, String> getPojoGettersValues(Object pojo) {
		Map<String, String> gettersValues = new HashMap<String, String>();
		for (Method m : pojo.getClass().getMethods())
		    if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
		      try {
				Object r = m.invoke(pojo);
				System.out.println(m.getName()+": "+r+" "+r.getClass().getTypeName());
		      } catch (IllegalAccessException e) {
					e.printStackTrace();
		      } catch (IllegalArgumentException e) {
					e.printStackTrace();
		      } catch (InvocationTargetException e) {
					e.printStackTrace();
		      }
		    }
		return gettersValues;
	}
	
	/**
	 * Prints the progress percentage in a for loop.
	 */
	public static void progressPercentage(int currentLoopIndex, int loopSize) {
		DecimalFormat df = new DecimalFormat("#.##");
		float perc = (float) currentLoopIndex*100/loopSize;
		int modPerc = (int) (perc % 10);
		if (modPerc==0) {
			System.out.println(currentLoopIndex+1+" of "+loopSize+" Progress....."+df.format(perc)+" %");
		}
		if (currentLoopIndex==loopSize-1) {
			System.out.println(currentLoopIndex+1+" of "+loopSize+" Finished 100 %");
		}
	}
	
	/**
     * Builds a path from a given date, ex.: 2010/2/23/11/54/10
     * @return
     */
    public static String dateToPath(Date date, int folderDepth){
        if(date==null){
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        List<String> path = new ArrayList<>();
        
        if (folderDepth  >0 && folderDepth <= 6) {
        	for (int i = 0; i < folderDepth; i++) {
            	
            	int calendarFieldNum = 0; 
            	int calendarFieldValue = 0;
            	int monthIncr = 0 ; 
            	
            	if (i==0) {
            		calendarFieldNum = Calendar.YEAR;            	
    			}
            	if (i==1) {
            		calendarFieldNum = Calendar.MONTH;
            		//In the Gregorian and Julian calendars JANUARY is 0
            		monthIncr = 1;
    			}
            	if (i==2) {
            		calendarFieldNum = Calendar.DAY_OF_MONTH;
    			}
            	if (i==3) {
            		calendarFieldNum = Calendar.HOUR_OF_DAY;
    			}
            	if (i==4) {
            		calendarFieldNum = Calendar.MINUTE;
    			}
            	if (i==5) {
            		calendarFieldNum = Calendar.SECOND;
    			}
            	calendarFieldValue = cal.get(calendarFieldNum)+monthIncr;
            	path.add(String.valueOf(calendarFieldValue));
    		}
		}else {
			System.out.println("Admitted max folder depth is 6: year/month/day/hour/minute/second");
		}
		return StringUtils.join(path, "/");
    }
    
}
