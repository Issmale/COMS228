package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author issmale bekri
 *
 */
public class ResellerTest {
	/**
	 * tests the constructor for reseller
	 */
	@Test
	void casualConstructorTest() {
		Town town = new Town(1,1);
		Reseller reseller = new Reseller(town, 1, 1);
		
		assertEquals(reseller.col, 1);
		assertEquals(reseller.row, 1);
		assertEquals(reseller.plain, town);
	}
	/**
	 * tests the who() method for reseller
	 */
	@Test
	void testWho() {
		Town town = new Town(2, 2); 
		Reseller reseller = new Reseller(town, 2, 2);
	    assertEquals(State.RESELLER, reseller.who());
	}
	/**
	 * tests the if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3)
	 */
	@Test
    void testA() {
        Town town = new Town(2,2);
        town.grid[0][0] = new Reseller(town,0,0);
        town.grid[0][1] = new Empty(town,0,1);
        town.grid[1][0] = new Empty(town,1,0);
        town.grid[1][1] = new Empty(town,1,1);
        assertEquals(State.EMPTY, town.grid[0][0].next(town).who());
    }
	/**
	 * tests the if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3)
	 */
	@Test
    void testB() {
        Town town = new Town(2,2);
        town.grid[0][0] = new Reseller(town,0,0);
        town.grid[0][1] = new Casual(town,0,1);
        town.grid[1][0] = new Casual(town,1,0);
        town.grid[1][1] = new Casual(town,1,1);
        assertEquals(State.EMPTY, town.grid[0][0].next(town).who());
    }
	/**
	 * tests the else if (nCensus[CASUAL]>= 5)
	 */
	@Test
	void testC() {
	    Town town = new Town(3, 3);
	    
	    town.grid[0][0] = new Casual(town, 0, 0);
	    town.grid[0][1] = new Casual(town, 0, 1);
	    town.grid[0][2] = new Casual(town, 0, 2);
	    town.grid[1][0] = new Casual(town, 1, 0);
	    town.grid[1][1] = new Reseller(town, 1, 1);
	    town.grid[1][2] = new Reseller(town, 1, 2);
	    town.grid[2][0] = new Casual(town, 2, 0);
	    town.grid[2][1] = new Casual(town, 2, 1);
	    town.grid[2][2] = new Casual(town, 2, 2);
	    
	    assertEquals(State.STREAMER, town.grid[1][1].next(town).who());
	} 
	
}
