import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.isEmpty()) return 0;
		int maxCount = 1;
		int currCount= 1;
		char ch = str.charAt(0);
		for(int i = 1; i<str.length();i++){
			if(str.charAt(i) == ch){
				currCount++;
			}else{
				if(maxCount<currCount) maxCount = currCount;
				currCount =1;
				ch = str.charAt(i);
			}
		}
		if(currCount>maxCount) maxCount = currCount;

		return maxCount; // YOUR CODE HERE
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		if(str.isBlank()) return "";
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i< str.length()-1; i++) {
			char ch = str.charAt(i);
			if(Character.isDigit(ch)){
					char nextChar = str.charAt(i+1);
					int num = Integer.parseInt(String.valueOf(ch));
					for(int j=0; j<num;j++){
							buffer.append(nextChar);
					}
			}else{
					buffer.append(ch);
			}
		}
		if(!Character.isDigit(str.charAt(str.length()-1))){
			buffer.append(str.charAt(str.length()-1));
		}
		String res = buffer.toString();
		return res; // YOUR CODE HERE
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		if(a.isEmpty() || b.isEmpty()||a.length()<len||b.length()<len) return false;
		HashSet<String> hashSet = new HashSet<String>();
		for(int i=0; i<a.length()-len+1; i++){
			String s = a.substring(i,i+len);
			hashSet.add(s);
		}
		for(int i=0; i<b.length()-len+1; i++){
			String s = b.substring(i,i+len);
			if(hashSet.contains(s)) return true;
		}
		return false; // YOUR CODE HERE
	}
}
