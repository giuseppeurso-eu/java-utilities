package eu.giuseppeurso.utilities.generics.strings;

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
}
