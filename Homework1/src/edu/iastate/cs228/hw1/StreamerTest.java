package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */

public class StreamerTest {
	/**
	 * tests the constructor for streamer
	 */
	@Test
	void casualConstructorTest() {
		Town town = new Town(2,3);
		Streamer streamer = new Streamer(town, 2, 3);
		
		assertEquals(streamer.col, 3);
		assertEquals(streamer.row, 2);
		assertEquals(streamer.plain, town);
	}
	/**
	 * tests the who method for streamer
	 */
	@Test
	void testWho() {
		Town town = new Town(5, 5); 
		Streamer streamer = new Streamer(town, 5, 5);
	    assertEquals(State.STREAMER, streamer.who());
	}
	/**
	 * tests the else if (nCensus[CASUAL]>= 5)
	 */
	@Test
	void testA() {
	    Town town = new Town(3, 3);
	    
	    town.grid[0][0] = new Casual(town, 0, 0);
	    town.grid[0][1] = new Streamer(town, 0, 1);
	    town.grid[0][2] = new Casual(town, 0, 2);
	    town.grid[1][0] = new Casual(town, 1, 0);
	    town.grid[1][1] = new Reseller(town, 1, 1);
	    town.grid[1][2] = new Reseller(town, 1, 2);
	    town.grid[2][0] = new Casual(town, 2, 0);
	    town.grid[2][1] = new Outage(town, 2, 1);
	    town.grid[2][2] = new Casual(town, 2, 2);
	    
	    assertEquals(State.STREAMER, town.grid[1][1].next(town).who()); 
	} 
	/**
	 * tests the else if (nCensus[OUTAGE] > 0)
	 */
	@Test
    void testB() {
        Town town = new Town(2,2);
        town.grid[0][0] = new Streamer(town,0,0);
        town.grid[0][1] = new Streamer(town,0,1);
        town.grid[1][0] = new Outage(town,1,0);
        town.grid[1][1] = new Outage(town,1,1);
        assertEquals(State.EMPTY, town.grid[0][0].next(town).who());
        
    }
}
