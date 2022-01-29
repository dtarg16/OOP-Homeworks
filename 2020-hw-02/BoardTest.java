import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated, s2, s2Rotated, stick,stickRotated,L1,L2,L3,L4,square;
	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.

	protected void setUp() throws Exception {
		b = new Board(3, 6);

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		// s
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		//2
		s2 = new Piece(Piece.S2_STR);
		s2Rotated = s.computeNextRotation();
		// stick
		stick = new Piece(Piece.STICK_STR);
		stickRotated = stick.computeNextRotation();
		// square
		square = new Piece(Piece.SQUARE_STR);
		// L
		L1 = new Piece(Piece.L1_STR);
		L2 = L1.computeNextRotation();
		L3 = L2.computeNextRotation();
		L4 = L3.computeNextRotation();

		b.place(pyr1, 0, 0);
	}

	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(3,b.getWidth());
		assertEquals(6,b.getHeight());
		assertTrue(b.getGrid(1,0));
		assertTrue(b.getGrid(1,1));
		assertTrue(b.getGrid(5,0));
		assertTrue(b.getGrid(-1,2));
		assertTrue(!b.getGrid(1,3));
		assertTrue(!b.getGrid(0,3));
		assertTrue(!b.getGrid(1,4));
		assertTrue(!b.getGrid(2,2));
	}

	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		//System.out.println(b.toString());
	}
	public void testPlaceoutOfBounds(){
		b.commit();
		b.place(stick,0,1);
		b.commit();
		int result = b.place(s2,0,5);
		assertEquals(Board.PLACE_OUT_BOUNDS,result);
		b.commit();
		result = b.place(square,2,2);
		assertEquals(Board.PLACE_OUT_BOUNDS,result);
		b.commit();
	}
	public void testPlaceBad(){
		b.commit();
		b.place(stick,0,1);
		b.commit();
		int result = b.place(s2,0,3);
		assertEquals(Board.PLACE_BAD,result);
		b.commit();
		result = b.place(square,1,1);
		assertEquals(Board.PLACE_BAD,result);
	}

	public void testPlace1(){
		b.commit();
		int dropHeight  = b.dropHeight(L2,0);
		assertEquals(2,dropHeight);
		int result = b.place(square,0,0);
		b.commit();
		assertEquals(result,Board.PLACE_BAD);
	}
	public void testPlace2(){
		b.commit();
		int result = b.place(stick,0,1);
		assertEquals(Board.PLACE_OK,result);
		b.commit();
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(5, b.getMaxHeight());
		//System.out.println(b.toString());
	}
	public void testPlace3(){
		b.commit();
		b.place(stick,0,1);
		b.commit();
		int result = b.place(s2,0,4);
		assertEquals(Board.PLACE_ROW_FILLED,result);
		b.commit();
		assertEquals(6, b.getColumnHeight(0));
		assertEquals(6, b.getColumnHeight(1));
		assertEquals(5, b.getColumnHeight(2));
		assertEquals(6, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(3, b.getRowWidth(4));
		assertEquals(2, b.getRowWidth(5));
	}
	public void testPlace4(){
		b.commit();
		int result = b.place(sRotated,1,1);
		assertEquals(Board.PLACE_OK,result);
		b.commit();
		result = b.place(pyr2,1,3);
		assertEquals(Board.PLACE_OK,result);
		b.commit();
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(5, b.getColumnHeight(1));
		assertEquals(6, b.getColumnHeight(2));
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(2, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));
		assertEquals(6, b.getMaxHeight());
		result = b.place(L1,0,1);
		assertEquals(Board.PLACE_BAD,result);
		b.commit();
		//System.out.println(b.toString());
	}

	public void testClearRows1(){
		b.commit();
		b.place(stick,0,1);
		b.commit();
		b.place(sRotated,1,1);
		b.commit();
		b.clearRows();
		b.commit();
		assertEquals(2, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(2, b.getMaxHeight());
		//System.out.println(b.toString());
	}
	public void testCommitUndo(){
		b.commit();
		b.place(stick,0,1);
		b.commit();
		b.place(sRotated,1,1);
		b.undo();
		b.commit();
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
		assertEquals(5, b.getMaxHeight());

		//System.out.println(b.toString());
	}
	public void testClearRows2(){
		b.commit();
		b.clearRows();
		b.commit();
		b.place(stick,0,2);
		b.commit();
		b.place(stick,1,2);
		b.commit();
		b.place(stick,2,2);
		b.commit();
		b.clearRows();
		b.commit();
		//System.out.println(b.toString());
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(1, b.getRowWidth(0));
	}

	// Make  more tests, by putting together longer series of
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.


}