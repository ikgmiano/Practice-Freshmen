import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean stop = false;
        
        do {
            displayMenu();
            int choice = scanner.nextInt();
            
            switch (choice) {
                // ACCOUNT CREATION
                case 1:
                    scanner.nextLine();
                    System.out.print("\nAccount Name: ");
                    String name = scanner.nextLine().toUpperCase();
                    System.out.print("Account Pin: ");
                    String pin = scanner.next();
                    System.out.print("Initial Deposit: ");
                    double amount = scanner.nextDouble();
                    BankAccount newAccount = new BankAccount(name, pin, amount);

                    displayAccountDetails(newAccount);
                    break;

                // ACCOUNT TRANSACTION
                case 2:
                    // Login prompt
                    System.out.print("\nEnter Account ID: ");
                    BankAccount account = getAccount();
                    if (account == null) {
                        System.out.println("Login cancelled, returning to main menu.");
                        break;
                    }

                    TRANSACTION_PROCESS:
                    while (true) {
                        displayTransactionMenu();
                        int transactionChoice = scanner.nextInt();
                        switch (transactionChoice) {
                            case 1:
                                if (deposit(account)) {
                                    break;
                                } else {
                                    continue;
                                }
                            case 2:
                                if (withdraw(account)) {
                                    break;
                                } else {
                                    continue;
                                }
                            case 3:
                                if (transfer(account)) {
                                    break;
                                } else {
                                    continue;
                                }
                            case 4:
                                balanceInquiry(account);
                                break;
                            case 5:
                                transactionHistory(account);
                                break;
                            default:
                                break TRANSACTION_PROCESS;
                        } 
                        
                        System.out.print("Do you want to perform another transaction? (1-Yes, 0-No): ");
                        if (scanner.nextInt() == 0) {
                            break;
                        }
                    }
                    break;

                // EXIT PROGRAM
                case 3:
                    System.out.println("\nThank you for using the banking system!");
                    stop = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (!stop);

        BankAccount.saveAccounts();
    }

    public static void displayMenu() {
        System.out.print(
            "\n- - - MAIN MENU - - - \n" +
            "1. Create an Account\n" +
            "2. Perform a Transaction\n" +
            "3. Exit\n" +
            "Enter your choice: "
        );
    }

    public static void displayTransactionMenu() {
        System.out.print(
            "\n- - SELECT YOUR TRANSACTION - - \n" +
            "1. Deposit\n" +
            "2. Withdrawal\n" +
            "3. Transfer\n" +
            "4. Balance Inquiry\n" +
            "5. Transaction History\n" +
            "6. Cancel\n" +
            "Enter your choice: "
        );
    }

    public static void displayAccountDetails(BankAccount account) {
        System.out.println(
            "\nAccount has been successfully created!\n" + 
            "- - - Account Details - - -\n" +
            "Account ID: " + account.getAccountId() + "\n" +
            "Name: " + account.getAccountName() + "\n" +
            "Returning to main menu."
        );
    }

    public static boolean deposit(BankAccount account) {
        double amount;

        while (true) {
            System.out.print("\nEnter Deposit Amount: ");
            amount = scanner.nextDouble();
    
            System.out.printf("You are depositing an amount of $%,.2f.\n", amount);
            System.out.print("(1-Confirm, 0-Cancel): ");
            if (scanner.nextInt() == 1) {
                try {
                    account.deposit(amount);
                    System.out.printf("You have successfully deposited an amount of $%,.2f.\n", amount);
                    return true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Amount cannot be negative.");
                }
            }

            System.out.print("Do you wish to deposit another amount? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                break;
            }
        }

        return false;
    }

    public static boolean withdraw(BankAccount account) {
        double amount;

        while (true) {
            System.out.print("\nEnter Withdrawal Amount: ");
            amount = scanner.nextDouble();

            System.out.printf("You are withdrawing an amount of $%,.2f.\n", amount);
            System.out.print("(1-Confirm, 0-Cancel): ");
            if (scanner.nextInt() == 1) {
                try {
                    account.withdraw(amount);
                    System.out.printf("You have successfully withdrew an amount of $%,.2f.\n", amount);
                    return true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Amount cannot be negative.");
                } catch (IllegalStateException e) {
                    System.out.println("You do not have enough balance for this transaction.");
                }
            }

            System.out.print("Do you wish to withdraw another amount? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                break;
            }
        }

        return false;
    }

    public static boolean transfer(BankAccount account) {
        int accountId;
        double amount;

        while (true) {
            System.out.print("\nEnter Recepient Account ID: ");
            accountId = scanner.nextInt();

            System.out.print("Enter Transfer Amount: ");
            amount = scanner.nextDouble();

            System.out.printf("You are transferring an amount of $%,.2f to an account with an ID of %d.\n", amount, accountId);
            System.out.print("(1-Confirm, 0-Cancel): ");
            if (scanner.nextInt() == 1) {
                try {
                    account.transfer(accountId, amount);
                    System.out.printf("You have succesfuly transferred an amount of $%,.2f to an account with an ID of %d.\n", amount, accountId);
                    return true;
                } catch (IllegalAccessError e) {
                    System.out.println("Cannot transfer funds to own account.");
                } catch (NullPointerException e) {
                    System.out.println("Recepient account ID is invalid or does not exist. Your funds has been returned.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Amount cannot be negative.");
                } catch (IllegalStateException e) {
                    System.out.println("You do not have enough balance for this transaction.");
                }
            }

            System.out.print("Do you wish to transfer another amount? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                break;
            }
        }

        return false;
    }

    public static void balanceInquiry(BankAccount account) {
        System.out.printf("\nYou account's current balance is $%,.2f.\n", account.balanceInquiry());
    }

    public static void transactionHistory(BankAccount account) {
        System.out.println("\n" + account.displayTransactionHistory());
    }

    public static BankAccount getAccount() {
        BankAccount account;
        while ((account = BankAccount.getBankAccount(scanner.nextInt())) == null) {
            System.out.println("Invalid, the account id does not exist.");
            System.out.print("Do you want to try again? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                return null;
            }
            System.out.print("Enter Account ID: ");
        }
        return account;
    }
}