import org.jetbrains.annotations.NotNull;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
    // Provided grid data for main/testing
    // The instance variable strategy is up to you.

    // Provided easy 1 6 grid
    // (can paste this text into the GUI too)
    public static final int[][] easyGrid = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 9");

    // Provided medium 5 3 grid
    public static final int[][] mediumGrid = Sudoku.stringsToGrid(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079");

    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
    public static final int[][] hardGrid = Sudoku.stringsToGrid(
            "3 7 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");


    public static final int SIZE = 9;  // size of the whole 9x9 puzzle
    public static final int PART = 3;  // size of each 3x3 part
    public static final int MAX_SOLUTIONS = 100;

    // Code bellow written by me
    private int[][] grid;
    private int solutionCount;
    private Vector<Spot> spots;
    private long startTime;
    private long finishTime;
    private int spotsCovered;
    private String solution = "";

    //Inner Class Spot that we use to store unassigned Spots;
    public class Spot implements Comparable<Spot> {
        private int x, y;

        public Spot(int x, int y) {
            super();
            if(x < 0 || x >= SIZE || y < 0 || y >= SIZE){
                throw new RuntimeException("Illegal coordinates");
            }
            this.x = x;
            this.y = y;
        }

        public void set(int value) {
            grid[this.x][this.y] = value;
        }


        public Vector<Integer> assignableNumbers() {
            Vector<Integer> numbers = new Vector<Integer>();
            //fill with
            for (int i = 1; i <= SIZE; i++) {
                numbers.add(i);
            }
            //check row and col // since row and col is fixed we can only use one loop;
            for (int i = 0; i < SIZE; i++) {
                if (grid[i][this.y] != 0 || grid[this.x][i] != 0) {
                    numbers.removeElement(grid[i][this.y]);
                    numbers.removeElement(grid[this.x][i]);
                }
            }
            //check box
            int minX = this.x / PART * PART;  // for 0 1 2  it is 0    for 3 4 5  it is 3    for 6 7 8 its 6
            int minY = this.y / PART * PART;
            for (int x = minX; x <= minX + 2; x++) {
                for (int y = minY; y <= minY + 2; y++) {
                    if (grid[x][y] > 0) {
                        numbers.removeElement(grid[x][y]);
                    }
                }
            }
            return numbers;
        }

        @Override
        public int compareTo(@NotNull Sudoku.Spot spot) {
            return this.assignableNumbers().size() - spot.assignableNumbers().size();
        }

    }
    public Spot createSpot(int x, int y){
        return new Spot(x,y);
    }
    /*------------------------------------------------------------------------*/
    // Provided various static utility methods to
    // convert data formats to int[][] grid.

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * The "..." is a Java 5 feature that essentially
     * makes "rows" a String[] array.
     * (provided utility)
     *
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String... rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            result[row] = stringToInts(rows[row]);
        }
        return result;
    }


    /**
     * Given a single string containing 81 numbers, returns a 9x9 grid.
     * Skips all the non-numbers in the text.
     * (provided utility)
     *
     * @param text string of 81 numbers
     * @return grid
     */
    public static int[][] textToGrid(String text) {
        int[] nums = stringToInts(text);
        if (nums.length != SIZE * SIZE) {
            throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
        }

        int[][] result = new int[SIZE][SIZE];
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row][col] = nums[count];
                count++;
            }
        }
        return result;
    }

    /**
     * Given a string containing digits, like "1 23 4",
     * returns an int[] of those digits {1 2 3 4}.
     * (provided utility)
     *
     * @param string string containing ints
     * @return array of ints
     */
    public static int[] stringToInts(String string) {
        int[] a = new int[string.length()];
        int found = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                a[found] = Integer.parseInt(string.substring(i, i + 1));
                found++;
            }
        }
        int[] result = new int[found];
        System.arraycopy(a, 0, result, 0, found);
        return result;
    }

    // Provided -- the deliverable main().
    // You can edit to do easier cases, but turn in
    // solving hardGrid.
    public static void main(String[] args) {
        runSudoku(easyGrid);
        runSudoku(mediumGrid);
        runSudoku(hardGrid);
    }

    /**
     * Sets up based on the given ints.
     */

    public Sudoku(int[][] ints) {
        // YOUR CODE HERE
        assert (ints.length == SIZE && ints[0].length == SIZE);
        solutionCount = 0;
        spotsCovered = 0;
        grid = new int[SIZE][SIZE];
        spots = new Vector<Spot>();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (ints[x][y] == 0) {
                    spots.add(new Spot(x, y));
                }
                grid[x][y] = ints[x][y];
            }
        }
        Collections.sort(spots);
        //remainingSpots();
    }
    public Sudoku(String values) {
        this(textToGrid(values));
    }

    /**
     * Solves the puzzle, invoking the underlying recursive search.
     */
    public int solve() {
        startTime = System.currentTimeMillis();
        recSolve();
        finishTime = System.currentTimeMillis();
        return solutionCount; // YOUR CODE HERE
    }

    public String getSolutionText() {
        return solution; // YOUR CODE HERE
    }

    public long getElapsed() {
        return finishTime - startTime; // YOUR CODE HERE
    }


    private void recSolve() {
        if (MAX_SOLUTIONS <= solutionCount) return;
        if (spots.size() == spotsCovered) {
            if (solutionCount == 0) storeFirstSolution();
            solutionCount++;
            return;
        }
        Spot spot = spots.get(spotsCovered);
        Vector<Integer> assignableNumbers = spot.assignableNumbers();
        //  if this happens no need to continue
        if (assignableNumbers.size() == 0) {
            return;
        }
        for (Integer assignableNumber : assignableNumbers) {
            spot.set(assignableNumber);
            spotsCovered += 1;
            recSolve();
            spot.set(0);
            spotsCovered -= 1;
        }
    }

    private static void runSudoku(int[][] difficulty) {
        Sudoku sudoku;
        sudoku = new Sudoku(difficulty);
        System.out.println(sudoku); // print the raw problem
        int count = sudoku.solve();
        System.out.println("solutions:" + count);
        System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
        System.out.println(sudoku.getSolutionText());
    }

    private void storeFirstSolution() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.append(grid[i][j]);
                if (j != SIZE - 1) result.append(" ");
            }
            if (i != SIZE - 1) result.append("\n");
        }
        solution = result.toString();
    }
}
