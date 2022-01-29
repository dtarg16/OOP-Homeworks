//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {

	private boolean grid[][];

	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid=grid;
	}
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	private boolean allTruesInRow(int col){
		for(int i=0; i<grid.length;i++){
			if(!grid[i][col]) return false;
		}
		return true;
	}
	private void clear(int col){
		for(int i=0; i<grid.length;i++) {
			for (int j = col; j < grid[0].length; j++) {
				if (j != grid[0].length-1) {
					grid[i][j] = grid[i][j + 1];
				} else {
					grid[i][j] = false;
				}
			}
		}
	}
	public void clearRows() {
		for(int i=0; i<grid[0].length; i++){
			if(allTruesInRow(i)){
				clear(i);
				clearRows();
			}
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid; // YOUR CODE HERE
	}
}
