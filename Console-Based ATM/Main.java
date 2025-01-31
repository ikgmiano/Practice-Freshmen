import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean stop = false;
        do {
            displayMenu();

            int choice = scanner.nextInt();
            switch (choice) {
                // Case for account creation
                case 1:
                    createAccount();
                    break;

                // Case for account transactions (e.g. deposit, withdrawal)
                case 2:
                    // Login prompt
                    BankAccount account = getAccount();
                    if (account == null) {
                        System.out.println("Login cancelled, returning to main menu.");
                        break;
                    }

                    // Security validation (pin)
                    boolean isSuccessful = authenticateAccountPin(account);
                    if (!isSuccessful) {
                        System.out.println("Authentication cancelled, returning to main menu.");
                        break;
                    }

                    TRANSACTION_PROCESS:
                    while (true) {
                        displayTransactionMenu();

                        int transactionChoice = scanner.nextInt();
                        switch (transactionChoice) {
                            // Case for deposit
                            case 1:
                                if (!deposit(account)) {
                                    continue;
                                }
                                break;

                            // Case for withdraw
                            case 2:
                                if (!withdraw(account)) {
                                    continue;
                                }
                                break;

                            // Case for transfer
                            case 3:
                                if (!transfer(account)) {
                                    continue;
                                }
                                break;

                            // Case for balance inquiry
                            case 4:
                                balanceInquiry(account);
                                break;

                            // Case for checking transaction history
                            case 5:
                                transactionHistory(account);
                                break;
                                
                            // Case for exiting from the transaction menu
                            default:
                                System.out.println("Transaction cancelled, returning to main menu.");
                                break TRANSACTION_PROCESS;
                        } 
                        
                        System.out.print("Do you want to perform another transaction? (1-Yes, 0-No): ");
                        if (scanner.nextInt() == 0) {
                            break;
                        }
                    }
                    break;

                // Case for exiting the ATM system
                case 3:
                    System.out.println("\nThank you for using the banking system!");
                    stop = true;
                    break;

                // Default case for invalid choice
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (!stop);

        BankAccount.saveAccounts();
        scanner.close();
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

    public static void createAccount() {
        scanner.nextLine();

        System.out.print("\nAccount Name: ");
        String name = scanner.nextLine().toUpperCase();
        System.out.print("Account Pin (6-digit): ");
        String pin;
        while (!isPinValid(pin = scanner.next())) {
            System.out.println("Invalid pin combination, please try again.");
            System.out.print("Account Pin (6-digit): ");
        }
        System.out.print("Initial Deposit: ");
        double amount = scanner.nextDouble();

        BankAccount newAccount = new BankAccount(name, pin, amount);

        displayAccountDetails(newAccount);
    }

    
    /**
     * This method prompts the user for a valid amount that will be added to the account object's balance.
     * 
     * @param account The account in which to deposit the amount to.
     * @return true if the transaction was valid and successful, false otherwise.
     */
    public static boolean deposit(BankAccount account) {
        double amount;
        // Prompts the user for the amount until the transaction is valid or cancelled 
        while (true) {
            System.out.print("\nEnter Deposit Amount: ");
            amount = scanner.nextDouble();
    
            System.out.printf("You are depositing an amount of $%,.2f.\n", amount);
            System.out.print("(1-Confirm, 0-Cancel): ");
            if (scanner.nextInt() == 1) {
                try {
                    account.deposit(amount);
                    System.out.printf("You have successfully deposited an amount of $%,.2f.\n", amount);
                    return true; // Transaction is valid
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); // Amount is negative
                }
            }

            // Prompts the user for retry if previous transaction was invalid or cancelled
            System.out.print("Do you wish to deposit another amount? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                return false; // Transaction cancelled
            }
        }
    }

    /**
     * 
     * This method prompts the user for a valid amount that will be deducted to the account object's balance.
     * 
     * @param account The account in which to withdraw the amount from.
     * @return true if the transaction was valid and successful, false otherwise.
     */
    public static boolean withdraw(BankAccount account) {
        double amount;
        // Prompts the user for the amount until the transaction is valid or cancelled
        while (true) {
            System.out.print("\nEnter Withdrawal Amount: ");
            amount = scanner.nextDouble();

            System.out.printf("You are withdrawing an amount of $%,.2f.\n", amount);
            System.out.print("(1-Confirm, 0-Cancel): ");
            if (scanner.nextInt() == 1) {
                try {
                    account.withdraw(amount);
                    System.out.printf("You have successfully withdrew an amount of $%,.2f.\n", amount);
                    return true; // Transaction is valid
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); // Amount is negative
                } catch (IllegalStateException e) {
                    System.out.println(e.getMessage()); // Amount is greater than the account's current balance
                }
            }

            // Prompts the user for retry if previous transaction was invalid or cancelled
            System.out.print("Do you wish to withdraw another amount? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                return false; // Transaction cancelled
            }
        }
    }

    /**
     * 
     * This method prompts the user for a valid amount that will be deducted to the account object's balance
     * and added to the recepient account's balance.
     * 
     * @param account The account in which to deduct the transfer amount from.
     * @return true if the transaction was valid and successful, false otherwise.
     */
    public static boolean transfer(BankAccount account) {
        int accountId;
        double amount;
        // Prompts the user for the amount until the transaction is valid or cancelled
        while (true) {
            System.out.print("\nEnter Recepient Account ID: "); // ID of the receiving account object
            accountId = scanner.nextInt();
            System.out.print("Enter Transfer Amount: ");
            amount = scanner.nextDouble();

            System.out.printf("You are transferring an amount of $%,.2f to an account with an ID of %d.\n", amount, accountId);
            System.out.print("(1-Confirm, 0-Cancel): ");
            if (scanner.nextInt() == 1) {
                try {
                    account.transfer(accountId, amount);
                    System.out.printf("You have succesfuly transferred an amount of $%,.2f to an account with an ID of %d.\n", amount, accountId);
                    return true; // Transaction is valid
                } catch (IllegalAccessError e) {
                    System.out.println(e.getMessage()); // Attempt to transfer to own account
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage()); // Recepient account ID is invalid or does not exist
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); // Amount is negative
                } catch (IllegalStateException e) {
                    System.out.println(e.getMessage()); // Amount is greater than the account's current balance
                }
            }

            // Prompts the user for retry if previous transaction was invalid or cancelled
            System.out.print("Do you wish to do another transfer? (1-Yes, 0-No): ");
            if (scanner.nextInt() == 0) {
                return false; // Transaction cancelled
            }
        }
    }
    
    /**
     * This method prints the current balance of the account object.
     * 
     * @param account The account for which to check the balance.
     */
    public static void balanceInquiry(BankAccount account) {
        System.out.printf("\nYou account's current balance is $%,.2f.\n", account.balanceInquiry());
    }

    /**
     * This method prints the transaction history of the account object. 
     * 
     * @param account The account for which to check the transaction history.
     */
    public static void transactionHistory(BankAccount account) {
        System.out.println("\n" + account.displayTransactionHistory());
    }

    public static BankAccount getAccount() {
        BankAccount account;
        System.out.print("\nEnter Account ID: ");
        // Prompts the user for an account ID until it's valid or attempt is cancelled
        while ((account = BankAccount.getBankAccount(scanner.nextInt())) == null) {
            System.out.println("Invalid, the account id does not exist.");
            System.out.print("Do you want to try again? (1-Yes, 0-No): ");

            if (scanner.nextInt() == 0) {
                return null; // Login attempt cancelled
            }

            System.out.print("Enter Account ID: ");
        }
        return account; // Valid account found
    }

    public static boolean authenticateAccountPin(BankAccount account) {
        System.out.print("\nEnter PIN Number: ");
        // Prompts the user for PIN until it's correct or attempt is cancelled
        while (!account.isPinValid(scanner.next())) {
            System.out.println("Invalid pin.");
            System.out.print("Do you want to try again? (1-Yes, 0-No): ");

            if (scanner.nextInt() == 0) {
                return false; // PIN Authentication cancelled
            }

            System.out.print("Enter PIN Number: ");
        }
        return true; // PIN is correct
    }

    private static boolean isPinValid(String pin) {
        // Checks if the pin length is valid
        if (pin.length() != 6) {
            return false;
        }
        // Checks if the pin contains digit only
        try {
            Integer.parseInt(pin);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}