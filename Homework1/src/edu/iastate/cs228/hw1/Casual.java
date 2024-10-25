package edu.iastate.cs228.hw1;

/**
 * 
 * @author issmale bekri
 *
 */


public class Casual extends TownCell {

	/**
	 * 
	 * @param town for town
	 * @param row for row integer
	 * @param col for col integer
	 * @return town, row, col
	 */
	public Casual(Town town, int row, int col) {
		super(town, row, col); 
		
	}

	/**
	 * @return the identity of the cell
	 */
	@Override
	public State who() {
		
		return State.CASUAL;
	}

	/**
	 * @return the cell type in the next cycle
	 */
	@Override
	public TownCell next(Town tNew) {
		
		int[] nCensus = new int[NUM_CELL_TYPE];
	    census(nCensus);
	    nCensus[CASUAL]--;

	    if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) { 
            return new Reseller(tNew, row, col);
        } else if (nCensus[RESELLER] > 0) { 
            return new Outage(tNew, row, col);
        } else if (nCensus[STREAMER] > 0 || nCensus[CASUAL] >= 5) { 
            return new Streamer(tNew, row, col);
        } else {
            return new Casual(tNew, row, col);
        }
	}
}


