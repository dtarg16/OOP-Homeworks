package hw5.model;


import java.util.HashMap;
public class AccountManager {
    private HashMap<String,String> accounts;

    public AccountManager(){
        accounts = new HashMap<String,String>();
        accounts.put("Patrick","1234");
        accounts.put("Molly","FloPup");
    }
    public boolean createAccount (String username, String password){
        if(accounts.containsKey(username)) return false;
        accounts.put(username, password);
        return true;
    }
    public boolean checkPassword(String username, String password){
        if(!accounts.containsKey(username)) return false;
        return (accounts.get(username).equals(password));
    }
    public int numAccounts(){
        return accounts.size();
    }
}