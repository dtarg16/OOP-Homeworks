// Board.java

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid,gridBackup;
	private boolean DEBUG = false;
	boolean committed;
	int[] widths,heights,widthsBackup,heightsBackup;
	int maxHeight,maxHeightBackup;

	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		committed = true;
		grid = new boolean[width][height];
		gridBackup = new boolean[width][height];
		//fill grid with with false values
		for (boolean[] row: grid)
			Arrays.fill(row, false);
		for (boolean[] row: gridBackup)
			Arrays.fill(row, false);
		// create and store 0s in widths and heights arrays
		widths = new int [height];
		Arrays.fill(widths,0);
		heights = new int [width];
		Arrays.fill(heights,0);
		widthsBackup = new int [height];
		//Arrays.fill(widthsBackup,0);
		heightsBackup = new int [width];
		//Arrays.fill(heightsBackup,0);
		// set maxHeight to 0
		maxHeight = 0;
		maxHeightBackup = 0;
		backupData();
		// YOUR CODE HERE
	}


	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		return maxHeight;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
			int w[] = new int[height];
			Arrays.fill(w,0);
			int h[] = new int[width];
			Arrays.fill(h,0);
			int mh=0;
			for(int x = 0; x < width; x ++){
				for(int y = 0; y < height; y++){
					if(grid[x][y]){
						h[x] = y+1;
						if(y+1>mh) mh = y+1;
						w[y]+=1;
					}
				}
			}
			if (mh != maxHeight) {
				throw new RuntimeException("Max height is different \n");
			}else if(!Arrays.equals(w,widths)){
				throw new RuntimeException("Widths array is different \n");
			}else if (!Arrays.equals(h,heights)){
				throw new RuntimeException("Heights array is different \n");
			}else {
				System.out.println("Wohoooo! Everything is correct");
			}
		}

	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		if(x <  0 || x + piece.getWidth() > width) return -1;
		int skirt[] = piece.getSkirt();
		int y = 0;
		for(int i =0; i <piece.getWidth();i++){
			int h = heights[x+i];
			y = Integer.max(y,h- skirt[i]);
		}
		return y; // YOUR CODE HERE
	}

	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x]; // YOUR CODE HERE
	}

	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		return widths[y];
		 // YOUR CODE HERE
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x >= width || y >= height || x < 0 || y < 0) return true;
		return grid[x][y]; // YOUR CODE HERE
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		int result = PLACE_OK;
		// YOUR CODE HERE

		committed = false;
		boolean filled = false;

		backupData();
		//check for each TPoint
		for(TPoint point : piece.getBody()){
			int pX = point.x+x;
			int pY = point.y+y;
			if(pX < 0 || pY < 0 || pX >= width || pY >= height){
				result  =  PLACE_OUT_BOUNDS;
			}else if(grid[pX][pY] == true){
				//if such point is already filled result must be place_bad----- No need to update width[] and height[]
				result = PLACE_BAD;
			}else {
				//if it was false we have to make it true; And update widths[] and height[] respectively
				grid[pX][pY] = true;
				widths[pY] += 1; //increase width by 1
				if(widths[pY] == width) filled = true; // if width of any row is equal to grid.width filled must be true
				if(heights[pX] <= pY){  //check if given Y coordinate is greater than height in this column and update
					heights[pX] = pY+1;
					if(pY>=maxHeight) maxHeight = pY+1;
				}
			}
		}
		sanityCheck();
		if(result == PLACE_OK && filled) return PLACE_ROW_FILLED;
		return result;
	}


	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(committed) backupData();
		int rowsCleared = 0;
		// YOUR CODE HERE
		for(int j=0; j<height; j++){
			if(widths[j] == width){
				clearAndDropDown(j);
				rowsCleared++;
				j--;
			}
		}
		maxHeight = 0 ;
		// update heights  and max height
		for(int x = 0; x < width; x ++){
			int heightOfCol = heights[x];
			heights[x] = 0;
			for(int y = heightOfCol-1; y >=0; y--){
				if(grid[x][y]){
					heights[x] = y+1;
					if(y+1>maxHeight) maxHeight = y+1;
					break;
				}
			}
		}
		sanityCheck();
		committed = false;
		return rowsCleared;
	}

	private void clearAndDropDown(int row){
		for (int j = row; j <maxHeight; j++) {
			for(int i=0; i<width;i++) {
				if (j != height-1) {
					grid[i][j] = grid[i][j + 1];
					widths[j] = widths[j+1];
				} else {
					grid[i][j] = false;
					widths[j] = 0;
				}
			}
		}
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		//swap pointers
		if(!committed) {
			int[] widthTemp = widths;
			widths = widthsBackup;
			widthsBackup = widthTemp;

			int[] heightsTemp = heights;
			heights = heightsBackup;
			heightsBackup = heightsTemp;

			boolean[][] gridTemp = grid;
			grid = gridBackup;
			gridBackup = gridTemp;

			int tmp = maxHeight;
			maxHeight = maxHeightBackup;
			maxHeightBackup = tmp;
			sanityCheck();
		}
		committed = true;
	}


	/**
	 Puts the board in the committed state.
	*/
	public void commit() {

			committed = true;
	}


	private void backupData(){
		//copy board data
		for(int x=0; x<width; x++){
			System.arraycopy(grid[x],0,gridBackup[x],0,height);
		}
		//copy heights array
		System.arraycopy(heights, 0, heightsBackup, 0, width);
		//copy width array
		System.arraycopy(widths, 0, widthsBackup, 0, height);
		maxHeightBackup = maxHeight;
	}

	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


