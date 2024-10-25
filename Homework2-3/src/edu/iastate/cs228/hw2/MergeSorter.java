package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Issmale Bekri
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		if (pts == null || pts.length <= 1) {
            return; 
        }

        int mid = pts.length / 2;
        Point[] left = new Point[mid];
        Point[] right = new Point[pts.length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = pts[i];
        }
        for (int i = mid; i < pts.length; i++) {
            right[i - mid] = pts[i];
        }

        mergeSortRec(left);
        mergeSortRec(right);

        merge(pts, left, right);
	
	}

	
	 /**
	  * Helper method to merge pts[]
	  * @param mergedPts
	  * @param left left side pts[]
	  * @param right right side pts[]
	  */
	 private void merge(Point[] mergedPts, Point[] left, Point[] right) {
	        int leftIndex = 0, rightIndex = 0, mergedIndex = 0;

	        while (leftIndex < left.length && rightIndex < right.length) {
	            if (pointComparator.compare(left[leftIndex], right[rightIndex]) <= 0) {
	                mergedPts[mergedIndex] = left[leftIndex];
	                leftIndex++;
	            } else {
	                mergedPts[mergedIndex] = right[rightIndex];
	                rightIndex++;
	            }
	            mergedIndex++;
	        }

	        while (leftIndex < left.length) {
	            mergedPts[mergedIndex] = left[leftIndex];
	            leftIndex++;
	            mergedIndex++;
	        }

	        while (rightIndex < right.length) {
	            mergedPts[mergedIndex] = right[rightIndex];
	            rightIndex++;
	            mergedIndex++;
	        }
	    }

}
