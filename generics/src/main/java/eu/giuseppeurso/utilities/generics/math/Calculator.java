package eu.giuseppeurso.utilities.generics.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



/**
 * Mathematics
 * @author Giuseppe Urso - www.giuseppeuros.eu
 *
 */
public class Calculator {
	
/**
 * Random integer from string
 * @param min
 * @param max
 */
static void randomNumberAsString(String min, String max, int total ){
	System.out.println("Range of numbers: ["+min+","+max+"]");
	System.out.println("Total of numbers: "+total);
	int mn = Integer.parseInt(min);
	int mx = Integer.parseInt(max);
	for (int i = 0; i < total; i++) {
		Random random = new Random();
		int randomNum = random.nextInt((mx - mn) + 1) + mn;
		System.out.println("Random String Number: "+ String.valueOf(randomNum));
	}
}

/**
 * Verifies if couples of integers are both greater than specified limits or not.
 * 
 * @param sideA
 * @param sideB
 * @param width limit 
 * @param height limit
 * @return
 */
static Boolean twoNumberGreaterThanLimits(int sideA, int sideB, int width, int height){
	System.out.println("Comparing "+sideA+" with limit "+width);
	System.out.println("Comparing "+sideB+" with limit "+height);
    return !(sideA < width && sideB < height);
}

/**
 * Get random integers in a given range.
 * @param min
 * @param max
 * @param totalNumbersToPrint
 */
static void printRandomNumbers(int min, int max, int totalNumbersToPrint ){
	System.out.println("Range of numbers: ["+min+","+max+"]");
	for (int i = 0; i < totalNumbersToPrint; i++) {
		Random random = new Random();
		int randomNum = random.nextInt((max - min) + 1) + min;
		System.out.println("Random Number: "+ randomNum);
	}
}

static int[] getRandomNumbers(int min, int max, int totalNumbersToGet ){
	int[] numbers = new int[totalNumbersToGet];
	System.out.println("Range of numbers: ["+min+","+max+"]");
	for (int i = 0; i < totalNumbersToGet; i++) {
		Random random = new Random();
		int randomNum = random.nextInt((max - min) + 1) + min;
		System.out.println("Generated random Number: "+ randomNum);
		// For uniqueness, check if the set to be returned contains the number generates each time
		for (int j = 0; j < numbers.length; j++) {
			System.out.println(j+") Current array number:"+numbers[j]);
			if (numbers[j]==randomNum) {
				System.out.println("Collision avoided for number: "+numbers[j]+"<>"+randomNum);
				randomNum = random.nextInt((max - min) + 1) + min;
			}
		}
		numbers[i] = randomNum;
		System.out.println("Saved number: "+numbers[i]);
		
	}
	return numbers;
}

static List<Integer> getRandomNumberList(int min, int max, int totalToGet){
	ArrayList<Integer> list = new ArrayList<Integer>();
    for (int i=min; i<max; i++) {
        list.add(new Integer(i));
    }
    Collections.shuffle(list);
    ArrayList<Integer> listWithRandomNumbers = new ArrayList<Integer>();
    for (int i=0; i<totalToGet; i++) {
    	listWithRandomNumbers.add(list.get(i));
    }
    return listWithRandomNumbers;
}
}