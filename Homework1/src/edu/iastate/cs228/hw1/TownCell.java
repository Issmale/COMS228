package edu.iastate.cs228.hw1;

/**
 * 
 * @author Issmale Bekri
 *
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	/**
	 * 
	 * @param town for Town
	 * @param row for integer row
	 * @param col for integer col
	 */
	public TownCell(Town town, int row, int col) {
		plain = town;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		
		for(int i = 0; i < NUM_CELL_TYPE; i++) {
			
			nCensus[i] = 0;
		}
				int plainGridLength = plain.grid.length;
				int highBound = Math.max(0, row - 1);
				int lowBound = Math.min(plainGridLength - 1, row + 1);
				int leftSide = Math.max(0, col - 1);
				int rightSide = Math.min(plainGridLength - 1, col + 1);
			
				for (int i = lowBound; i >= highBound; i--)
				{
					for (int j = rightSide; j >= leftSide; j--)
					{
						if (plain.grid[i][j].who() == State.RESELLER)
						{
							nCensus[RESELLER]++;
						} else if (plain.grid[i][j].who() == State.EMPTY)
						{
							nCensus[EMPTY]++;
						} else if (plain.grid[i][j].who() == State.CASUAL)
						{
							nCensus[CASUAL]++;
						} else if (plain.grid[i][j].who() == State.OUTAGE)
						{
							nCensus[OUTAGE]++;
						} else if (plain.grid[i][j].who() == State.STREAMER)
						{
							nCensus[STREAMER]++;
						}
					}
				}
			}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}

