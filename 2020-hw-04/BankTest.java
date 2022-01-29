import junit.framework.TestCase;

public class BankTest extends TestCase {
    public void testBankSingleThread() throws InterruptedException {
            Bank bank = new Bank(1);
            bank.processFile("bank_test.txt",1);
            assertEquals(2,bank.numberOfTransactions(0));
            assertEquals(1080,bank.accountBalance(0));
            assertEquals(11,total_transactions(bank));
    }
    public void testBankMultiThread() throws InterruptedException {
        Bank bank = new Bank(4);
        bank.processFile("bank_test.txt",2);
        assertEquals(2,bank.numberOfTransactions(0));
        assertEquals(1080,bank.accountBalance(0));
        assertEquals(11,total_transactions(bank));
    }
    public void testBank100kmultithread() throws InterruptedException {
        Bank bank = new Bank (16);
        bank.processFile("100k.txt",16);
        assertEquals(100000,total_transactions(bank));
        for(int i=0; i<20; i++){
            assertEquals(1000,bank.accountBalance(i));
        }

    }
    private int total_transactions(Bank bank){
        int res=0;
        for(int i=0; i<20; i++){
            res+=bank.numberOfTransactions(i);
        }
        return res/2;
    }
}


