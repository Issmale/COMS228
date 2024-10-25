package edu.iastate.cs228.hw1;

/**
 * 
 * @author issmale bekri
 *
 */

public class Empty extends TownCell{

	/**
	 * 
	 * @param town for Town
	 * @param row for the integer row
	 * @param col for the integer col
	 * @return town, row, col
	 */
	public Empty(Town town, int row, int col) {
		super(town, row, col);
		
	}

	/**
	 * returns the identity of the class
	 */
	@Override
	public State who() {
		
		return State.EMPTY;
	}

	/**
	 * the cell type in the next cycle
	 */
	@Override
	public TownCell next(Town tNew) {
		
		int[] nCensus = new int[NUM_CELL_TYPE];
	    census(nCensus);
	    nCensus[EMPTY]--;

	    if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
	    	return new Reseller(tNew, row, col);
	    }else {
	    	return new Casual(tNew, row, col);
	    }
	}

}
