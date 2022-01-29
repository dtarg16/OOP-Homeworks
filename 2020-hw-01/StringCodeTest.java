// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

import junit.framework.TestCase;

public class StringCodeTest extends TestCase {
    //
    // blowup
    //
    public void testBlowup1() {
        // basic cases
        assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
        assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
    }

    public void testBlowup2() {
        // things with digits

        // digit at end
        assertEquals("axxx", StringCode.blowup("a2x3"));

        // digits next to each other
        assertEquals("a33111", StringCode.blowup("a231"));

        // try a 0
        assertEquals("aabb", StringCode.blowup("aa0bb"));
    }
    // xo agdebs tavidan ra dishla undaaa
    public void testBlowup3() {
        // weird chars, empty string
        assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
        assertEquals("", StringCode.blowup(""));

        // string with only digits
        assertEquals("", StringCode.blowup("2"));
        assertEquals("33", StringCode.blowup("23"));

        //string with single symbol
        assertEquals("b",StringCode.blowup("b"));
        assertEquals(".",StringCode.blowup("."));
    }


    //
    // maxRun
    //
    public void testRun1() {
        assertEquals(2, StringCode.maxRun("hoopla"));
        assertEquals(3, StringCode.maxRun("hoopllla"));
    }

    public void testRun2() {
        assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
        assertEquals(0, StringCode.maxRun(""));
        assertEquals(3, StringCode.maxRun("hhhooppoo"));
    }

    public void testRun3() {
        // "evolve" technique -- make a series of test cases
        // where each is change from the one above.
        assertEquals(1, StringCode.maxRun("123"));
        assertEquals(2, StringCode.maxRun("1223"));
        assertEquals(2, StringCode.maxRun("112233"));
        assertEquals(3, StringCode.maxRun("1112233"));
    }

    // Need test cases for stringIntersect
    //
    // stringIntersect
    //
    public void testIntersect1(){
        //Basic cases
        assertEquals(true, StringCode.stringIntersect("a","a",1));
        assertEquals(true, StringCode.stringIntersect("abc","abcde",2));
        assertEquals(true, StringCode.stringIntersect("xyabcyx","jkabckj",3));
        assertEquals(true, StringCode.stringIntersect("qwertya","zxacvb",1));
        assertEquals(true, StringCode.stringIntersect("12345kj","qkj",2));
        assertEquals(true, StringCode.stringIntersect("12345kj","12",2));
    }


    public void testIntersect2(){
        //big string
        assertEquals(true, StringCode.stringIntersect("as:JDlbsasa./.;[asdasdas11234rtyuiasdASdasxas","aadvdfvouh[o`1234rtyuisdSdbcde",8));
        assertEquals(true, StringCode.stringIntersect("xedwe- 123210hlmCSVan;vfvaouu","kkkksodf123210hlmCSVanOSDFPSjdfp",10));
        //multiple choice
        assertEquals(true, StringCode.stringIntersect("abcd","abcde",2));
        assertEquals(true, StringCode.stringIntersect("xyabcyxxxxxx","jkabckjxxxxxx",3));
        assertEquals(true, StringCode.stringIntersect("qwerty","asdfqwertyzxcvbqwerty",1));
    }


    public void testIntersect3(){
        //false cases

        // #empty string > 0
        assertEquals(false, StringCode.stringIntersect("","abcde",1));
        assertEquals(false, StringCode.stringIntersect("abc","",2));
        assertEquals(false, StringCode.stringIntersect("","",2));
        //
        assertEquals(false, StringCode.stringIntersect("abcdefg","adefghi",6));
        assertEquals(false, StringCode.stringIntersect("abc","abcdefg",4));
        assertEquals(false, StringCode.stringIntersect("asdasd","hljhlk",1));
        //len > string len
        assertEquals(false, StringCode.stringIntersect("abcdefg","adefghi",17));
        assertEquals(false, StringCode.stringIntersect("abc","abcdefg",4));
        assertEquals(false, StringCode.stringIntersect("asdasd","hljhlk",10));
    }

}


















