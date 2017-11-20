package eu.giuseppeurso.utilities.generics.strings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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
}
