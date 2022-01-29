// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.concurrent.CountDownLatch;

public class Cracker {
    // Array of chars used to produce strings
    public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private  CountDownLatch latch;
	private String hash;
    private int maxLen;
    private String password;
    private Boolean lastCracked = false;

    /*
     Given a byte[] array, produces a hex String,
     such as "234a6f". with 2 chars for each byte in the array.
     (provided code)
    */
    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val < 16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

	/*
     Given a string of hex byte values such as "24a26f", creates
     a byte[] array of those values, one byte value -128..127
     for each 2 chars.
     (provided code)
    */
//	public static byte[] hexToArray(String hex) {
//		byte[] result = new byte[hex.length()/2];
//		for (int i=0; i<hex.length(); i+=2) {
//			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
//		}
//		return result;
//	}


    public String generate(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes());
        return hexToString(md.digest());
    }

    private void crackSolver(int start, int end, String soFar) throws NoSuchAlgorithmException, InterruptedException {
    	//base case string length has exceeded maxLen
        if (soFar.length() > maxLen) {
			return;
		}
		if(generate(soFar).equals(hash)){
			password = soFar;
			lastCracked = true;
			return;
		}
        for (int i = start; i < end; ++i) {
            String current = soFar + CHARS[i];
            crackSolver(0, CHARS.length, current);
        }
    }

    public String crack(String hash, int maxLen, int numThreads) throws NoSuchAlgorithmException, InterruptedException {
    	this.maxLen = maxLen;
    	this.hash = hash;
		latch = new CountDownLatch(numThreads);
    	int piece = CHARS.length/numThreads;
    	for(int i=0; i<numThreads; i++){
			Worker w;
    		if(i!=numThreads-1){
				w = new Worker(i*piece,(i+1)*piece);
			}else{
				w = new Worker(i*piece,CHARS.length);
			}
    		Thread t = new Thread(w);
    		t.start();

    	}
    	latch.await();
    	if(lastCracked){
    		lastCracked = false;
			return password;
		}
    	return null;
    }

    private class Worker implements Runnable {
    	int start,end;
    	public Worker(int start,int end){
			this.start = start;
			this.end = end;
			//System.out.println(start+ " " + end);
    	}
        @Override
        public void run() {
			try {
				crackSolver(start,end,"");
				latch.countDown();
			} catch (NoSuchAlgorithmException | InterruptedException e) {
				e.printStackTrace();
			}
		}

    }

//    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
//        Cracker cracker = new Cracker();
//        if (args.length < 2) {
//            System.out.println(cracker.generate(args[0]));
//
//        } else {
//            // args: targ len [num]
//            String targ = args[0];
//            int len = Integer.parseInt(args[1]);
//            int num = 1;
//            if (args.length > 2) {
//                num = Integer.parseInt(args[2]);
//            }
//			System.out.println(cracker.crack(targ,len,num));
//			System.out.println("All Done");
//		}
//        // YOUR CODE HERE
//    }
}
