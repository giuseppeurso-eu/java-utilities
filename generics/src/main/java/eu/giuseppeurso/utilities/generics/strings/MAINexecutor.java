package eu.giuseppeurso.utilities.generics.strings;

import java.util.StringJoiner;

public class MAINexecutor {

	public static void main(String[] args) {
		String input = "openpa:test1;openpa:test2;openpa:test3";
			   
		String[] splitted = StringPlayer.splitByDelimiter(input, ";");
		
		StringJoiner joiner = new StringJoiner(";");
		String oldChar=":";
		String newChar="-";
		System.out.println("Replacing "+oldChar+" with "+newChar);
		for (int i = 0; i < splitted.length; i++) {
			
			joiner.add(splitted[i].replace(oldChar, newChar));
		}
		System.out.println("TOTAL String joined: "+splitted.length);
		System.out.println(joiner.toString());
		
				
	}
	
	
	

}
