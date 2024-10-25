package edu.iastate.cs228.hw1;

public class Reseller extends TownCell{

	/**
	 * 
	 * @param town for Town
	 * @param row for the integer row
	 * @param col for the integer col
	 * @return town, row, col
	 */
	public Reseller(Town town, int row, int col) {
		super(town, row, col);
		
	}

	/**
	 * returns the identity of the cell
	 */
	@Override
	public State who() {
		return State.RESELLER;
	}

	/**
	 * returns the cell for the next cycle
	 */
	@Override
	public TownCell next(Town tNew) {
		
		int[] nCensus = new int[NUM_CELL_TYPE];
	    census(nCensus);
	    nCensus[RESELLER]--;

	    
	    if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3) {
	        return new Empty(tNew, row, col);
	    } else if (nCensus[CASUAL]>= 5) { 
	    	return new Streamer(tNew, row, col);
	    }else {
	        return new Reseller(tNew, row, col); 
	    }
		
	}

}
