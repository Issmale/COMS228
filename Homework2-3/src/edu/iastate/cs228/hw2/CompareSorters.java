package edu.iastate.cs228.hw2;

/**
 *  
 * @author Issmale Bekri
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		PointScanner[] scanner = new PointScanner[4];
		Random rand = new Random();
		int numTrial = 1;
		int input = 0;

		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println("Keys: 1 (random integers) 2 (file input) 3 (exit)");

		try (Scanner scnr = new Scanner(System.in)) {
			while (true) {
			    System.out.print("Trial " + numTrial + ": ");
			    input = scnr.nextInt();

			    if (input == 1) {
			        System.out.print("Enter number of random points: ");
			        Point[] pts = generateRandomPoints(scnr.nextInt(), rand);

			        for (int i = 0; i < 4; i++) {
			            scanner[i] = new PointScanner(pts, Algorithm.values()[i]);
			            scanner[i].scan();
			        }
			    } else if (input == 2) {
			        System.out.println("Points from a file");
			        System.out.print("File name: ");
			        scnr.nextLine();
			        String fileName = scnr.nextLine();

			        for (int i = 0; i < 4; i++) {
			            scanner[i] = new PointScanner(fileName, Algorithm.values()[i]);
			            scanner[i].scan();
			        }
			    }
			    else if(input == 3) {
			    	System.out.println("Exited");
			    	break;
			    }
			    else {
			    	throw new InputMismatchException("Please enter 1, 2 or 3.");
			    }

			    System.out.println("algorithm size  time (ns)");
			    System.out.println("----------------------------------");

			    for (int i = 0; i < 4; i++) {
	                System.out.println(scanner[i].stats());
	            }
			    System.out.println("----------------------------------");
			    numTrial++;
			}

			scnr.close();
		} catch (InputMismatchException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
	        throw new IllegalArgumentException();
	    }

	    Point[] randArr = new Point[numPts];
	    int randNumX; 
	    int randNumY;
	    int counter = 0;

	    while (numPts >= 1) {
	        randNumX = rand.nextInt(101) - 50;
	        randNumY = rand.nextInt(101) - 50;
	        randArr[counter] = new Point(randNumX, randNumY);
	        numPts--;
	        counter++;
	    }

	    return randArr;
	}
	

}
