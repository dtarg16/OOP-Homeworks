import junit.framework.TestCase;
import org.junit.Before;

import java.util.*;

import static org.junit.Assert.assertTrue;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece stick,stickRotated;
	private Piece square;
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;
	private Piece s2, s2Rotated;
	private Piece pL1,pL2,pL3,pL4;
	private Piece mpL1,mpL2,mpL3,mpL4;
	private Piece[] pieces;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		//pyramid
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		// s
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		// s2
		s2 = new Piece(Piece.S2_STR);
		s2Rotated = s.computeNextRotation();
		// stick
		stick = new Piece(Piece.STICK_STR);
		stickRotated = stick.computeNextRotation();
		// square
		square = new Piece(Piece.SQUARE_STR);
		// L
		pL1 = new Piece(Piece.L1_STR);
		pL2 = pL1.computeNextRotation();
		pL3 = pL2.computeNextRotation();
		pL4 = pL3.computeNextRotation();
		// mirrored L
		mpL1 = new Piece(Piece.L2_STR);
		mpL2 = mpL1.computeNextRotation();
		mpL3 = mpL2.computeNextRotation();
		mpL4 = mpL3.computeNextRotation();
		//Piecec[] pieces
		pieces = Piece.getPieces();

	}
	public void testFullRotation(){
		Piece testPiece = pieces[Piece.L1];
		assertTrue(testPiece.fastRotation().fastRotation().fastRotation().fastRotation().fastRotation().equals(pL2));
	}

	// Here are some sample tests to get you started
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());

		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());

		// Check size of sMirrored piece
		assertEquals(3, s2.getWidth());
		assertEquals(2, s2.getHeight());

		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, s2Rotated.getWidth());
		assertEquals(3, s2Rotated.getHeight());


		// Check size of mL piece
		assertEquals(2, mpL1.getWidth());
		assertEquals(3, mpL1.getHeight());

		// Now try after 1 rotation
		assertEquals(3, mpL2.getWidth());
		assertEquals(2, mpL2.getHeight());
		// Now try after 2 rotation
		assertEquals(2, mpL3.getWidth());
		assertEquals(3, mpL3.getHeight());

		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}

	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0},mpL1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,1,0},mpL2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,1},s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,0},sRotated.getSkirt()));
	}


	// Test the rotation
	public void testPyramidRotation(){
		assertEquals(true, equalsBodyAndString(pyr2,"0 1 1 0 1 1 1 2"));
		assertEquals(true, equalsBodyAndString(pyr3,"1 0 1 1 2 1 1 0"));
		assertEquals(true, equalsBodyAndString(pyr4,"0 0 0 1 0 2 1 1"));
		assertEquals(true, pyr1.equals(pyr4.computeNextRotation()));
		assertEquals(false, pyr2.equals(pyr3.computeNextRotation()));
		assertEquals(false, pyr3.equals(pyr4.computeNextRotation()));
		assertEquals(false, pyr1.equals(pyr3.computeNextRotation()));
		assertEquals(false, pyr2.equals(pyr4.computeNextRotation()));
	}

	 // Test fast rotation
	public void testFastRotation(){
		//testing L piece
		Piece L1Test = pieces[Piece.L1];
		assertEquals(true, equalsBodyAndString(L1Test,"0 0 0 1 0 2 1 0"));
		Piece L2Test = L1Test.fastRotation();
		assertEquals(true, equalsBodyAndString(L2Test,"0 0 1 0 2 0 2 1"));
		Piece L3Test = L2Test.fastRotation();
		assertEquals(true, equalsBodyAndString(L3Test,"0 2 1 0 1 1 1 2"));
		Piece L4Test = L3Test.fastRotation();
		assertEquals(true, equalsBodyAndString(L4Test,"0 0 0 1 1 1 2 1"));
		L1Test = L4Test.fastRotation();
		assertEquals(true, equalsBodyAndString(L1Test,"0 0 0 1 0 2 1 0"));
		//testing s2 rotated
		Piece s2Test = pieces[Piece.S2];
		assertEquals(true,equalsBodyAndString(s2Test,"0 1 1 1 1 0 2 0"));
		Piece s2RotatedTest = s2Test.fastRotation();
		assertEquals(true,equalsBodyAndString(s2RotatedTest,"0 0 0 1 1 1 1 2"));
		//testing square
		Piece squareTest = pieces[Piece.SQUARE];
		assertEquals(true,equalsBodyAndString(squareTest,"0 0 0 1 1 0 1 1"));
		Piece squareRotated = squareTest.fastRotation();
		assertEquals(true,squareTest.equals(squareRotated));
	}

	public void testEqual(){
		//mirrored L
		Piece mL1test = pieces[Piece.L2];
		assertTrue(mL1test.equals(mpL1));
		assertTrue(mL1test.getHeight()==3);
		assertTrue(mL1test.getWidth()==2);
		assertTrue(Arrays.equals(new int[] {0, 0},mL1test.getSkirt()));
		assertTrue(mL1test.computeNextRotation().equals(pieces[Piece.L2].fastRotation()));
		assertTrue(mL1test.fastRotation().getHeight()==2);
		assertTrue(mL1test.fastRotation().getWidth()==3);
		assertTrue(Arrays.equals(new int[] {1, 1, 0},mL1test.fastRotation().getSkirt()));
		//stick
		Piece stickTest = pieces[Piece.STICK];
		assertTrue(stickTest.equals(stick));
		assertEquals(false,stickTest.fastRotation().equals(stick));
		assertTrue(stickTest.fastRotation().equals(stick.computeNextRotation()));
	}


	private boolean equalsBodyAndString(Piece piece, String string) {
		Piece stringPiece = new Piece(string);
		TPoint[] stringArray = stringPiece.getBody();
		TPoint[] body = piece.getBody();
		List<TPoint>  bodyList = Arrays.asList(body);
		List<TPoint> stringList = Arrays.asList(stringArray);
		if(bodyList.size()!=stringList.size()) return false;
		return bodyList.containsAll(stringList);
	}
}
