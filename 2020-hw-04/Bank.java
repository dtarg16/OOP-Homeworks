// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

public class Bank {
    public static final int ACCOUNTS = 20;     // number of accounts
    public static final int InitialBalance = 1000;
    private final Transaction nullTrans = new Transaction(-1, 0, 0);
    private Buffer buffer;
    private int NumWorkers;
    private List<Account> accounts;


    public Bank(int numWorkers) {
        this.buffer = new Buffer();
        this.NumWorkers = numWorkers;
        accounts = new ArrayList<>();
        for (int i = 0; i < ACCOUNTS; i++) {
            Account acc = new Account(this, i, InitialBalance);
            accounts.add(acc);
        }
    }

    /*
     Reads transaction data (from/to/amt) from a file for processing.
     (provided code)
     */
    public void readFile(String file) throws InterruptedException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Use stream tokenizer to get successive words from file
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            while (true) {
                int read = tokenizer.nextToken();
                if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
                int from = (int) tokenizer.nval;

                tokenizer.nextToken();
                int to = (int) tokenizer.nval;

                tokenizer.nextToken();
                int amount = (int) tokenizer.nval;

                // Use the from/to/amount
                // YOUR CODE HERE
                Transaction transaction = new Transaction(from, to, amount);
                buffer.enqueue(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (int i = 0; i < NumWorkers; i++) {
            buffer.enqueue(nullTrans);
        }
    }

    /*
     Processes one file of transaction data
     -fork off workers
     -read file into the buffer
     -wait for the workers to finish
    */
    public void processFile(String file, int numWorkers) throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numWorkers);

        for (int i = 0; i < numWorkers; i++) {
            Worker worker = new Worker();
            executor.execute(worker);
        }
        readFile(file);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        this.printAccounts();
    }


    private class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Transaction transaction = buffer.dequeue();
                    if (transaction.getFrom() == -1) break;
                    processTransaction(transaction);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
          //  latch.countDown();
        }

        private void processTransaction(Transaction transaction) {
        	int amount = transaction.getAmount();
            int from = transaction.getFrom();
            int to = transaction.getTo();
            accounts.get(to).updateBalance(amount);
            accounts.get(from).updateBalance(-amount);
        }
    }
    public int numberOfTransactions(int client_id){
        return accounts.get(client_id).getTransactions();
    }
    public int accountBalance(int client_id){
        return accounts.get(client_id).getBalance();
    }


    /*
     Looks at commandline args and calls Bank processing.
    */
//    public static void main(String[] args) throws InterruptedException {
//        // deal with command-lines args
//        if (args.length == 0) {
//            System.out.println("Args: transaction-file [num-workers [limit]]");
//            System.exit(1);
//        }
//
//        String file = args[0];
//
//        int numWorkers = 1;
//        if (args.length >= 2) {
//            numWorkers = Integer.parseInt(args[1]);
//        }
//        // YOUR CODE HERE
//        //latch = new CountDownLatch(numWorkers);
//        Bank bank = new Bank(numWorkers);
//        bank.processFile(file, numWorkers);
//    }

    private void print(Object obj) {
        System.out.println(obj);
    }

    private void printAccounts() {
        for (Account acc : accounts) {
            print(acc.toString());
        }
    }
}




