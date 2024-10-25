package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Issmale Bekri
 *
 */
public class Town {
	
	private int length, width;  
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		
		try {
		    File file = new File(inputFileName);
		    Scanner scnr = new Scanner(file);

		    int length = scnr.nextInt();
		    int width = scnr.nextInt();
		    this.length = length;
		    this.width = width;
		    grid = new TownCell[length][width];
		    scnr.nextLine(); 
		    for (int row = 0; row < length; row++) {
		        String[] arr = scnr.nextLine().split("\\s+"); 
		        if (arr.length != width) {
		            throw new IllegalArgumentException("Invalid input file: Inconsistent row width.");
		        }

		        for (int col = 0; col < arr.length; col++) {
		            switch (arr[col].trim()) { 
		                case "R":
		                    grid[row][col] = new Reseller(this, row, col);
		                    break;
		                case "E":
		                    grid[row][col] = new Empty(this, row, col);
		                    break;
		                case "C":
		                    grid[row][col] = new Casual(this, row, col);
		                    break;
		                case "O":
		                    grid[row][col] = new Outage(this, row, col);
		                    break;
		                case "S":
		                    grid[row][col] = new Streamer(this, row, col);
		                    break;
		                default:
		                    throw new IllegalArgumentException("Invalid cell type in the input file.");
		            }
		        }
		    }
		    scnr.close();
		} catch (FileNotFoundException e) {
		    throw new IllegalArgumentException("Input file not found.");
		} catch (Exception e) {
		    throw new IllegalArgumentException("Error parsing input file: " + e.getMessage());
		}
	}
	
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random random = new Random(seed);
	    for (int row = 0; row < length; row++) {
	        for (int col = 0; col < width; col++) {
	            int randNum = random.nextInt(TownCell.NUM_CELL_TYPE);
	            State state = State.values() [randNum];
	            if (state.equals(State.CASUAL)) {
	                grid[row][col] = new Casual(this, row, col);
	            } else if (state.equals(State.EMPTY)) {
	                grid[row][col] = new Empty(this, row, col);
	            } else if (state.equals(State.OUTAGE)) {
	                grid[row][col] = new Outage(this, row, col);
	            } else if (state.equals(State.RESELLER)) {
	                grid[row][col] = new Reseller(this, row, col);
	            } else if (state.equals(State.STREAMER)) {
	                grid[row][col] = new Streamer(this, row, col);
	            }
	            else {
	            	throw new IllegalArgumentException();
	            }
	        }
	    }
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String str = "";
	    for (int row = 0; row < length; row++) {
	        for (int col = 0; col < width; col++) {
	            str += grid[row][col].who().toString().charAt(0); 
	            str += " "; 
	        }
	        str += "\n"; 
	    }
	    return str;
	}
}
