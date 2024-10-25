package edu.iastate.cs228.hw1;

/**
 * 
 * @author issmale bekri
 *
 */
public class Outage extends TownCell{

	/**
	 * 
	 * @param town for Town
	 * @param row for integer row
	 * @param col for integer col
	 */
	public Outage(Town town, int row, int col) {
		super(town, row, col);
		
	}

	/**
	 * returns the identity of the cell
	 */
	@Override
	public State who() {
		
		return State.OUTAGE;
	}

	/**
	 * returns the the cell in the next cycle
	 */
	@Override
	public TownCell next(Town tNew) {
		
		int[] nCensus = new int[NUM_CELL_TYPE];
	    census(nCensus);
	    nCensus[OUTAGE]--;

		return new Empty(tNew, row, col);
	}
}
