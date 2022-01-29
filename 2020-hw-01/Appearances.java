import java.util.*;

public class Appearances {

	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		HashMap<T,Integer> A = new HashMap<>();
		for (T element : a){
			if(A.containsKey(element)){
				A.put(element,A.get(element)+1);
			} else{
				A.put(element,1);
			}
		}
		HashMap<T,Integer> B = new HashMap<>();
		for (T element : b){
			int hashCode = element.hashCode();
			if(B.containsKey(element)) {
				B.put(element,B.get(element)+1);
			}else{
				B.put(element,1);
			}
		}
		int count = 0;
		for(T key : A.keySet()){
			if(B.containsKey(key)){
				if(A.get(key)== B.get(key)){
					count++;
				}
			}
		}

		return count;
			// YOUR CODE HERE
	}

}
