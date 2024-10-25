
package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */

public class CasualTest {
	
	/**
	 * tests the constructor for casual
	 */
	@Test
	void casualConstructorTest() {
		Town town = new Town(2,3);
		Casual casual = new Casual(town, 2, 0);
		
		assertEquals(casual.col, 0);
		assertEquals(casual.row, 2);
		assertEquals(casual.plain, town);
	}
	/**
	 * tests the who method for casual
	 */
	@Test
	void testWho() {
		Town town = new Town(5, 5); 
	    Casual casual = new Casual(town, 2, 2);
	    assertEquals(State.CASUAL, casual.who());
	}
	/**
	 * tests the if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) rule
	 */
	@Test
    void testA() {
        Town town = new Town(2,2);
        town.grid[0][0] = new Casual(town,0,0);
        town.grid[0][1] = new Streamer(town,0,1);
        town.grid[1][0] = new Outage(town,1,0);
        town.grid[1][1] = new Casual(town,1,1);
        assertEquals(State.RESELLER, town.grid[0][0].next(town).who());
    }
	
	/**
	 * tests the else if (nCensus[STREAMER] > 0 || nCensus[CASUAL] >= 5)
	 */
	@Test
	void testB() {
	    Town town = new Town(3, 3);
	    
	    town.grid[0][0] = new Empty(town, 0, 0);
	    town.grid[0][1] = new Empty(town, 0, 1);
	    town.grid[0][2] = new Empty(town, 0, 2);
	    town.grid[1][0] = new Streamer(town, 1, 0);
	    town.grid[1][1] = new Casual(town, 1, 1);
	    town.grid[1][2] = new Streamer(town, 1, 2);
	    town.grid[2][0] = new Streamer(town, 2, 0);
	    town.grid[2][1] = new Streamer(town, 2, 1);
	    town.grid[2][2] = new Streamer(town, 2, 2);
	    
	    assertEquals(State.STREAMER, town.grid[1][1].next(town).who()); 
	} 


}
