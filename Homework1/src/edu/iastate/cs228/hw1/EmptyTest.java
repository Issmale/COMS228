package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */

public class EmptyTest {

	
	/**
	 * tests the constructor for empty
	 */
	@Test
	void casualConstructorTest() {
		Town town = new Town(2,3);
		Empty empty = new Empty(town, 4, 4);
		
		assertEquals(empty.col, 4);
		assertEquals(empty.row, 4);
		assertEquals(empty.plain, town);
	}
	/**
	 * tests the who() method for empty
	 */
	@Test
	void testWho() {
		Town town = new Town(3, 3); 
	    Empty empty = new Empty(town, 2, 2);
	    assertEquals(State.EMPTY, empty.who());
	}
	/**
	 * tests if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) 
	 */
	@Test
    void testA() {
        Town town = new Town(2,2);
        town.grid[0][0] = new Empty(town,0,0);
        town.grid[0][1] = new Casual(town,0,1);
        town.grid[1][0] = new Casual(town,1,0);
        town.grid[1][1] = new Casual(town,1,1);
        assertEquals(State.RESELLER, town.grid[0][0].next(town).who());
    }
	/**
	 * tests else{} from the empty class
	 */
	@Test
    void testB() {
        Town town = new Town(2, 2);
        town.grid[0][0] = new Empty(town, 0, 0);
        town.grid[0][1] = new Empty(town, 0, 1);
        town.grid[1][0] = new Outage(town, 1, 0); 
        town.grid[1][1] = new Outage(town, 1, 1);

      
        assertEquals(State.CASUAL, town.grid[0][0].next(town).who());
}
}
