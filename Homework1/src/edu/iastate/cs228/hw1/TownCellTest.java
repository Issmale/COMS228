package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */
public class TownCellTest {
	
	    /**
	     * tests the who method 
	     */
	    @Test
	    public void testWho() {
	        Town town = new Town(3, 3);
	        TownCell cell = new Casual(town, 1, 1);

	        assertEquals(State.CASUAL, cell.who());
	    }
	    /**
	     * tests the census method
	     */
	    @Test
	    void censusTest() {
	    	Town town = new Town(4,4);
	    	town.randomInit(10);
	    	Casual casual = new Casual(town,1,1);
	    	town.grid[1][1] = casual;
	    	int[] nCensus = new int[5];
	    	casual.census(nCensus);
	    	assertEquals("[1, 2, 2, 3, 1]", Arrays.toString(nCensus));
	    }
}