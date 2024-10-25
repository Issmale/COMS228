package edu.iastate.cs228.hw1;
/**
 * 
 * @author issmale bekri
 *
 */
public class Streamer extends TownCell {
	/**
	 * 
	 * @param town for Town
	 * @param row for integer row
	 * @param col for integer col
	 * @return town, row, col
	 */
	public Streamer(Town town, int row, int col) {
		super(town, row, col);
		
	}
	

	/**
	 * @return identity for the cell
	 */
	@Override
	public State who() {
		
		return State.STREAMER;
	}

	/**
	 * @return the cell for the next cycle
	 */
	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[NUM_CELL_TYPE];
	    census(nCensus);	
	    nCensus[STREAMER]--;

	    if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
	    	return new Reseller(tNew, row, col);
	    }else if (nCensus[RESELLER] > 0) {
	    	return new Outage(tNew, row, col);
	    }else if (nCensus[OUTAGE] > 0) {
	    	return new Empty(tNew, row, col);
	    }else if (nCensus[CASUAL]>= 5) {
	    	return new Streamer(tNew, row, col);
	    }else {
	    	return new Streamer(tNew, row, col); 
	    }
	    }	
	}


