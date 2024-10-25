package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Issmale Bekri
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
	    for (int row = 0; row < tOld.getLength(); row++) {
	        for (int col = 0; col < tOld.getWidth(); col++) {
	            tNew.grid[row][col] = tOld.grid[row][col].next(tNew);
	        }
	    }
	    return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		int townLength = town.getLength();
		int townWidth = town.getWidth();
		for(int row = 0; row < townLength; row++) {
			for(int col = 0; col < townWidth; col++) {
				if(town.grid[row][col].who() ==  State.CASUAL) {
					profit ++;
			}
		}	
	}
	return profit;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		Scanner scnr = new Scanner(System.in);
		Town town = null;
		System.out.println("How to populate grid (type 1 or 2): 1: from file. 2: randomly with seed");
		int choice = scnr.nextInt();
		int rows = 0;
		int cols = 0;
		if(choice == 1) {
			System.out.println("Please enter file path: ");
			try {
				String filePath = scnr.next();
				town = new Town(filePath);
			} catch (FileNotFoundException e) {
				town = null;
				System.out.println("File not found.");
			}
		}else {
			System.out.println("Provide rows, cols and seed integer separated by spaces: ");
			rows = scnr.nextInt();
			cols = scnr.nextInt();
			int seed = scnr.nextInt();
			town = new Town(rows, cols);
			town.randomInit(seed);
		} 
		scnr.close();

		if(town != null){
			int profit = 0;
			int townLength = town.getLength();
			int townWidth = town.getWidth();
			double potentialProfit = townLength * townWidth;
			for(int i = 0; i < 12; i++){
				if(i != 0) {
					System.out.println(" After itr: " + i);
				}
				System.out.println(town.toString());
				System.out.println("Profit: " + getProfit(town));
				profit += getProfit(town);
				town = updatePlain(town);
			}
			double profitPercentage = profit * 100.0 / potentialProfit;
			double averageProfit = profitPercentage / 12.0;
			System.out.printf("%.2f", averageProfit);
			System.out.print("%");
		
		}
	}
}


