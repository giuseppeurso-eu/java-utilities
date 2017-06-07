package eu.giuseppeurso.utilities.generics.math;



public class MAINexecutor {
	
	public static void main(String argv[]) {
		
		//Calculator - randomNumberAsString()
		//randomNumberAsString("3", "10", 5);
		
		//Calculator - isGreaterThan()
		int sideA=20;
		int sideB=10;
		int limitA=30;
		int limitB=35;
		if (Calculator.twoNumberGreaterThanLimits(sideA,sideB,limitA,limitB)) {
			System.out.println("At least one of sides is greater than specified limit:");
		} else {
			System.out.println("Both given numbers are not greater than limits specified!");
		}
		
	}

}
