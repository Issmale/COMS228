package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;


import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
/**
 * 
 * @author issmale bekri
 *
 */

public class TownTest {
	
	/**
	 * tests the getWidth method
	 */
	@Test
    public void testGetWidth() {
        Town town = new Town(3, 4);
        assertEquals(4, town.getWidth());
    }

	/**
	 * tests the getLength method
	 */
    @Test
    public void testGetLength() {
        Town town = new Town(3, 4);
        assertEquals(3, town.getLength());
    }

    /**
     * tests the randomInit method
     */
    @Test
    public void testRandomInit() {
        Town town = new Town(3, 4);
        int seed = 123;
        town.randomInit(seed);
        for (int row = 0; row < town.getLength(); row++) {
            for (int col = 0; col < town.getWidth(); col++) {
                assertNotNull(town.grid[row][col]);
            }
        }
    }

  
}