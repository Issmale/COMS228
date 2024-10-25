package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author Issmale Bekri
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null || pts.length == 0) {
			System.out.println(pts.length);
			throw new IllegalArgumentException();
		}
		
		
		points = new Point[pts.length];
		for(int i = 0; i < pts.length; i ++) {
			
				points[i] = pts[i];
		}
		
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File inputFile = new File(inputFileName);
	    Scanner scnr;
	    sortingAlgorithm = algo;

	    try {
	        scnr = new Scanner(inputFile);
	        int count = 0;

	        while (scnr.hasNextInt()) {
	            scnr.nextInt();
	            count++;
	        }

	        if (count % 2 != 0) {
	            scnr.close();
	            throw new InputMismatchException();
	        }

	        points = new Point[count / 2];
	        scnr.close();

	        scnr = new Scanner(inputFile);
	        int ptsCount = 0;

	        while (scnr.hasNextInt()) {
	            points[ptsCount] = new Point(scnr.nextInt(), scnr.nextInt());
	            ptsCount++;
	        }

	        scnr.close();
	    } catch (FileNotFoundException e) {
	        throw new FileNotFoundException();
	    }
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter;

	    switch (sortingAlgorithm) {
	        case SelectionSort:
	            aSorter = new SelectionSorter(points);
	            break;
	        case InsertionSort:
	            aSorter = new InsertionSorter(points);
	            break;
	        case MergeSort:
	            aSorter = new MergeSorter(points);
	            break;
	        case QuickSort:
	            aSorter = new QuickSorter(points);
	            break;
	        default:
	            throw new IllegalArgumentException();
	    }

		long time = System.nanoTime();

		aSorter.setComparator(0);
		aSorter.sort();
		Point p = aSorter.getMedian();

		aSorter.setComparator(1);
		aSorter.sort();
		Point b = aSorter.getMedian();

		scanTime = System.nanoTime() - time;

		medianCoordinatePoint = new Point(p.getX(), b.getY());
	}
	
     /**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{

		    return String.format("%-14s %-6d %-12d", sortingAlgorithm.name(), points.length, scanTime);
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		scan();
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";


		
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		// TODO 
		String fileName = "points.txt";

		try {
			FileWriter writeFile = new FileWriter(fileName);
			writeFile.write(toString());
			writeFile.close();
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
		
	}	

	public Point getMedianCoordinatePoint() {
	    return medianCoordinatePoint;
	}

		
}
