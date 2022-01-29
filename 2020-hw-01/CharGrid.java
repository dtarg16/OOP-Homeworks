// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int minRow=0;
		int maxRow=0;
		int minCol=0;
		int maxCol=0;
		int totalAppearance = 0;
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[i].length;j++){
				if(grid[i][j] == ch){
					totalAppearance++;
					if(totalAppearance == 1){
						minRow = i;
						maxRow = i;
						minCol = j;
						maxCol = j;
					}
					if(i<minRow) minRow = i;
					if(i>maxRow) maxRow = i;
					if(j<minCol) minCol = j;
					if(j>maxCol) maxCol = j;
				}
			}
		}
		if(totalAppearance<=1) return totalAppearance;
		return (maxRow-minRow+1)*(maxCol-minCol+1);
		// YOUR CODE HERE
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		if(grid.length<3 || grid[0].length<3) return 0;
		int count = 0;
		for(int i=1; i< grid.length-1; i++){
			for(int j=1; j<grid[i].length-1; j++){
				int left  = countArmlength(i,j,0,-1);
				int right = countArmlength(i,j,0,1);
				int top = countArmlength(i,j,-1,0);
				int bot = countArmlength(i,j,-1,0);
				if(left>0 && left == right && left == top && left == bot) count++;
			}
		}

		return count; // YOUR CODE HERE
	}

	private int countArmlength(int i, int j, int row_delta,int col_delta){
		if((i+row_delta) <0 || (i+row_delta==grid.length) ||(j+col_delta<0) || (j+col_delta==grid[0].length )) return 0;
		if (grid[i][j] != grid[i+row_delta][j+col_delta]) return 0;
		return 1+ countArmlength(i+row_delta,j+col_delta,row_delta,col_delta);
	}
}
	

