package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.Before;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */

public class ISPBusinessTest {
	
	/**
	 * tests the method census()
	 */
	@Test
	public void testCensus() 
	{
		Town town = new Town(3,3);
        town.grid[0][0] = new Empty(town,0,0);
        town.grid[0][1] = new Empty(town,0,1);
        town.grid[0][2] = new Reseller(town,0,2);
        town.grid[1][0] = new Casual(town,1,0);
        town.grid[1][1] = new Casual(town,1,1);
        town.grid[1][2] = new Streamer(town,1,2);
        town.grid[2][0] = new Outage(town,0,2);
        town.grid[2][1] = new Outage(town,0,2);
        town.grid[2][2] = new Casual(town,0,2);   
		assertEquals(3, ISPBusiness.getProfit(town));
	}
	
	/**
	 * tests the method updatePlain()
	 */
	 @Test
	    public void testUpdatePlain() {
	        Town town = new Town(4, 4);

	        for (int row = 0; row < 4; row++) {
	            for (int col = 0; col < 4; col++) {
	                town.grid[row][col] = new Casual(town, row, col);
	            }
	        }
	        Town newTown = ISPBusiness.updatePlain(town);
	        assertNotEquals(State.CASUAL, newTown.grid[0][0].who());
	    }

	 
	  /**
	   * tests the getProfit() method
	   */
	  @Test
	    public void testGetProfit() {
	        Town town = new Town(4, 4);

	        for (int row = 0; row < 4; row++) {
	            for (int col = 0; col < 4; col++) {
	                town.grid[row][col] = new Casual(town, row, col);
	            }
	        }
	        int expectedProfit = 4 * 4; 
	        int actualProfit = ISPBusiness.getProfit(town);
	        assertEquals(expectedProfit, actualProfit);
	    }
}
