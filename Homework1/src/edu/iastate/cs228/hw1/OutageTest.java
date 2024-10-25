package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */
public class OutageTest {
	/**
	 * tests constructor for outage
	 */
	@Test
	void casualConstructorTest() {
		Town town = new Town(4,3);
		Outage outage = new Outage(town, 2, 4);
		
		assertEquals(outage.col, 4);
		assertEquals(outage.row, 2);
		assertEquals(outage.plain, town);
	}
	/**
	 * tests the who() method for outage
	 */
	@Test
	void testWho() {
		Town town = new Town(2, 2); 
	    Outage outage = new Outage(town, 2, 2);
	    assertEquals(State.OUTAGE, outage.who());
	}
	/**
	 * tests the next method for outage
	 */
	
	@Test
    void testA() {
        Town town = new Town(2,2);
        town.grid[0][0] = new Outage(town,0,0);
        town.grid[0][1] = new Casual(town,0,1);
        town.grid[1][0] = new Outage(town,1,0);
        town.grid[1][1] = new Streamer(town,1,1);
        assertEquals(State.EMPTY, town.grid[0][0].next(town).who());
    }

}