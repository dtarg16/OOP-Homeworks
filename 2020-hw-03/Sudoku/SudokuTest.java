import junit.framework.TestCase;


import java.util.Vector;

public class SudokuTest extends TestCase {
    public static final int[][] notSolvable = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 3");

    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
    public static final int[][] veryHardGrid = Sudoku.stringsToGrid(
            "8 0 0 0 0 0 0 0 0",
            "0 0 3 6 0 0 0 0 0",
            "0 7 0 0 9 0 2 0 0",
            "0 5 0 0 0 7 0 0 0",
            "0 0 0 0 4 5 7 0 0",
            "0 0 0 1 0 0 0 3 0",
            "0 0 1 0 0 0 0 6 8",
            "0 0 8 5 0 0 0 1 0",
            "0 9 0 0 0 0 4 0 0");

    public static final int[][] manySolutionGrid = Sudoku.stringsToGrid(
            "3 0 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");

    public static final int[][] mediumGrid = Sudoku.textToGrid("530070000600195000098000060800060003400803001700020006060000280000419005000080079");

    public void testSpot() {
        Sudoku sudoku = new Sudoku(mediumGrid);
        int solutions = sudoku.solve();
        assertEquals(1, solutions);
        Sudoku.Spot spot = sudoku.createSpot(0, 0);
        Vector<Integer> assNums = spot.assignableNumbers();
        assertEquals(2, assNums.size());
        assertTrue(assNums.contains(1));
        assertTrue(assNums.contains(2));
        try {
            Sudoku.Spot spot1 = sudoku.createSpot(-1, 2);
        } catch (RuntimeException e) {
            assertEquals("Illegal coordinates", e.getMessage());
        }
    }

    public void testAssertError() {
        try {
            String s = "530070";
            Sudoku sudoku = new Sudoku(Sudoku.textToGrid(s));
        } catch (RuntimeException e) {
            assertEquals("Needed 81 numbers, but got:6", e.getMessage());
        }
    }

    public void testnotSolvable() {
        Sudoku sudoku = new Sudoku(notSolvable);
        int solutions = sudoku.solve();
        assertEquals(0, solutions);
        assertEquals("", sudoku.getSolutionText());
    }

    public void testManySolution() {
        Sudoku sudoku = new Sudoku(manySolutionGrid);
        int solutions = sudoku.solve();
        assertEquals(6, solutions);

    }

    public void testveryHardsGrid() {
        Sudoku sudoku = new Sudoku(veryHardGrid);
        int solutions = sudoku.solve();
        assertEquals(1, solutions);
        String solutionText = sudoku.getSolutionText();
        String realSolution = stringBuilder("8 1 2 7 5 3 6 4 9",
                "9 4 3 6 8 2 1 7 5",
                "6 7 5 4 9 1 2 8 3",
                "1 5 4 2 3 7 8 9 6",
                "3 6 9 8 4 5 7 2 1",
                "2 8 7 1 6 9 5 3 4",
                "5 2 1 9 7 4 3 6 8",
                "4 3 8 5 2 6 9 1 7",
                "7 9 6 3 1 8 4 5 2");
        assertEquals(realSolution, solutionText);
    }


    private String stringBuilder(String... rows) {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < rows.length; row++) {
            result.append(rows[row]);
            if (row != rows.length - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}


