import java.io.*;
import java.util.ArrayList;

public class BankAccount implements Serializable {
    private static UniqueIdGenerator uniqueIdGenerator = UniqueIdGenerator.getInstance();
    private static ArrayList<BankAccount> listOfAccounts = loadAccounts();

    private int accountId;
    private String accountName;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    BankAccount(String accountName, String pin, double amount) {
        this.accountId = uniqueIdGenerator.getNextUniqueId();
        this.accountName = accountName;
        this.pin = pin; // limit pin to 4-6 digits only.
        this.balance = (amount >= 0) ? amount : 0;
        this.transactionHistory.add(String.format("Initial Deposit: \t+%,.2f", amount));
        listOfAccounts.add(this);
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }

        balance += amount;
        transactionHistory.add(String.format("Deposit: \t\t+%,.2f", amount));
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (amount > balance) {
            throw new IllegalStateException("Amount is greater than the current balance.");
        }

        balance -= amount;
        transactionHistory.add(String.format("Withdrawal: \t\t\t\t\t-%,.2f", amount));
    }

    public void transfer(int accountId, double amount) {
        BankAccount recipientAccount = getBankAccount(accountId); 
        if (accountId == this.accountId) {
            throw new IllegalAccessError("Cannot transfer to own account.");
        }
        if (recipientAccount == null) {
            throw new NullPointerException("Account ID does not exist.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        if (amount > balance) {
            throw new IllegalStateException("Amount is greater than the current balance.");
        }

        recipientAccount.receive(amount);
        balance -= amount;
        transactionHistory.add(String.format("Transfer: \t\t\t\t\t-%,.2f", amount));
    }

    private void receive(double amount) {
        balance += amount;
        transactionHistory.add(String.format("Receive: \t\t+%,.2f", amount));
    }

    public double balanceInquiry() {
        return balance;
    }

    public String displayTransactionHistory() {
        String string = "";

        for (String transaction : transactionHistory) {
            string += transaction + "\n";
        }
        string += String.format("Current balance: \t %,.2f", balance);

        return string;
    }

    // GETTER METHODS
    public int getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public boolean isPinValid(String pin) {
        if (pin == this.pin) {
            return true;
        }
        return false;
    }

    public static BankAccount getBankAccount(int accountId) {
        for (BankAccount account : listOfAccounts) {
            if (account.accountId == accountId) {
                return account;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<BankAccount> loadAccounts() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("accounts.ser"))) {
            return (ArrayList<BankAccount>) objectInputStream.readObject();
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public static void saveAccounts() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("accounts.ser"))) {
            objectOutputStream.writeObject(listOfAccounts);;
        } catch (Exception e) {}
    }

    @Override
    public String toString() {
        return "Account Id: " + accountId + "\n" +
               "Account Name: " + accountName + "\n" +
               "Balance: " + balance;
    }
}
