// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {
    private List<String> stringToList(String s) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<s.length(); i++) {
            list.add(String.valueOf(s.charAt(i)));
            // note: String.valueOf() converts lots of things to string form
        }
        return list;
    }
    private List<Integer> intToList(String s) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<s.length(); i++) {
            list.add(Integer.valueOf(s.charAt(i)-'0'));
            // note: String.valueOf() converts lots of things to string form
        }
        return list;
    }
    private Set<Integer> intToSet(String s) {
        Set<Integer> list = new HashSet<>();
        for (int i=0; i<s.length(); i++) {
            list.add(Integer.valueOf(s.charAt(i)-'0'));
            // note: String.valueOf() converts lots of things to string form
        }
        return list;
    }
    private Set<String> stringToSet(String s) {
        Set<String> list = new HashSet<>();
        for (int i=0; i<s.length(); i++) {
            list.add(String.valueOf(s.charAt(i)));
            // note: String.valueOf() converts lots of things to string form
        }
        return list;
    }
    public void testNoFollow1(){
        //T = String  test. simple
        List<String> t = stringToList("abapac");
        Taboo taboo = new Taboo(t);
        Set<String> a = stringToSet("bpc");
        Set<String> b = stringToSet("a");
        Set<String> c = stringToSet("");
        assertEquals(a,taboo.noFollow("a"));
        assertEquals(b,taboo.noFollow("b"));
        assertEquals(c,taboo.noFollow("c"));
    }

    public void testNoFollow2(){
        //T = Integer  test.  simple
        List<Integer> t = intToList("1223642514523");
        Taboo taboo = new Taboo(t);
        Set<Integer> one = intToSet("24");
        Set<Integer> two = intToSet("235");
        Set<Integer> three = intToSet("6");
        Set<Integer> four = intToSet("25");
        Set<Integer> five = intToSet("12");
        Set<Integer> six  = intToSet("4");
        Set<Integer> seven = intToSet("");

        assertEquals(one,taboo.noFollow(1));
        assertEquals(two,taboo.noFollow(2));
        assertEquals(three,taboo.noFollow(3));
        assertEquals(four,taboo.noFollow(4));
        assertEquals(five,taboo.noFollow(5));
        assertEquals(six,taboo.noFollow(6));
        assertEquals(seven,taboo.noFollow(7));
    }

    public void testNoFollow3(){
        // string with null
        List<String> left = stringToList("abc");
        List<String> right = stringToList("dbe");
        List<String> t = new ArrayList<>();
        t.addAll(left);
        t.add(null);
        t.addAll(right);
        Taboo taboo = new Taboo(t);
        Set<String> a = stringToSet("b");
        Set<String> b = stringToSet("ce");
        Set<String> c = stringToSet("");
        Set<String> d = stringToSet("b");
        Set<String> e = stringToSet("");
        assertEquals(a,taboo.noFollow("a"));
        assertEquals(b,taboo.noFollow("b"));
        assertEquals(c,taboo.noFollow("c"));
        assertEquals(d,taboo.noFollow("d"));
        assertEquals(e,taboo.noFollow("e"));
    }

    public void testNoFollow4(){
        //multiple nulls-Integer
        List<Integer> left  = intToList("12236");
        List<Integer> mid  = intToList("45456");
        List<Integer> right  = intToList("41245");
        List<Integer> t = new ArrayList<>();
        t.addAll(left);
        t.add(null);
        t.add(null);
        t.addAll(mid);
        t.add(null);
        t.addAll(right);
        Taboo taboo = new Taboo(t);
        //122364NULLNULL45456NULL41245
        Set<Integer> one = intToSet("2");
        Set<Integer> two = intToSet("234");
        Set<Integer> three = intToSet("6");
        Set<Integer> four = intToSet("15");
        Set<Integer> five = intToSet("46");
        Set<Integer> six  = intToSet("");
        Set<Integer> seven = intToSet("");
        assertEquals(one,taboo.noFollow(1));
        assertEquals(two,taboo.noFollow(2));
        assertEquals(three,taboo.noFollow(3));
        assertEquals(four,taboo.noFollow(4));
        assertEquals(five,taboo.noFollow(5));
        assertEquals(six,taboo.noFollow(6));
        assertEquals(seven,taboo.noFollow(7));
    }
    public void testReduce1(){
        List<String> t = stringToList("abapcd");
        Taboo taboo = new Taboo(t);
        List<String> before = stringToList("cbacbapd");
        List<String> after = stringToList("cbcbpd");
        taboo.reduce(before);
        assertEquals(after,before);
    }

    public void testReduce2(){
        //
        List<String> left = stringToList("abc");
        List<String> right = stringToList("deca");
        List<String> t = new ArrayList<>();
        t.addAll(left);
        t.add(null);
        t.addAll(right);
        Taboo taboo = new Taboo(t);
        List<String> before = stringToList("adbcacdeba");
        List<String> after = stringToList("adbacdba");
        taboo.reduce(before);
        assertEquals(after,before);
    }
    public void testReduce3(){
        //
        List<Integer> left = intToList("1234");
        List<Integer> mid = intToList("567");
        List<Integer> right = intToList("890");
        List<Integer> t = new ArrayList<>();
        t.add(null);
        t.addAll(left);
        t.add(null);
        t.addAll(mid);
        t.add(null);
        t.addAll(right);
        t.add(null);
        Taboo taboo = new Taboo(t);
        List<Integer> before = intToList("1234567890");
        List<Integer> after = intToList("135780");
        taboo.reduce(before);
        assertEquals(after,before);
        List<Integer> before2 = intToList("123433465358655798689768");
        List<Integer> after2 = intToList("13336535865579868768");
        taboo.reduce(before2);
        assertEquals(after2,before2);

    }
    public void testReduce4(){
        //
        List<Integer> left = intToList("11");
        List<Integer> mid = intToList("22");
        List<Integer> right = intToList("33");
        List<Integer> t = new ArrayList<>();
        t.add(null);
        t.addAll(left);
        t.add(null);
        t.addAll(mid);
        t.add(null);
        t.addAll(right);
        t.add(null);
        Taboo taboo = new Taboo(t);
        List<Integer> before = intToList("1111111111111222222222222222233333333333");
        List<Integer> after = intToList("123");
        taboo.reduce(before);
        assertEquals(after,before);
        List<Integer> before2 = intToList("1111111111111");
        List<Integer> after2 = intToList("1");
        taboo.reduce(before2);
        assertEquals(after2,before2);

    }


}
