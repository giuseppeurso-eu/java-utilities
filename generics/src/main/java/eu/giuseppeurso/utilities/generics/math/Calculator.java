package eu.giuseppeurso.utilities.generics.math;

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

}