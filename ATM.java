import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String date;
    private String type;
    private double amount;

    public Transaction(String date, String type, double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Transaction transaction = new Transaction(getCurrentDate(), "Withdrawal", amount);
            transactionHistory.add(transaction);
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        Transaction transaction = new Transaction(getCurrentDate(), "Deposit", amount);
        transactionHistory.add(transaction);
        System.out.println("Deposit successful. Current balance: " + balance);
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            Transaction transaction = new Transaction(getCurrentDate(), "Transfer to " + recipient.getUserId(), amount);
            transactionHistory.add(transaction);
            System.out.println("Transfer successful. Current balance: " + balance);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getDate() + " - " + transaction.getType() + ": " + transaction.getAmount());
        }
    }

    private String getCurrentDate() {
        
        return "01-01-2023";
    }
}

public class ATM {
    public static void main(String[] args) {
        Account account = new Account("naren", "naren1234", 1000.0);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (account.getUserId().equals(userId) && account.validatePin(pin)) {
            System.out.println("Authentication successful.");
            boolean quit = false;
            while (!quit) {
                System.out.println("\nATM Functionalities:");
                System.out.println("1. Transactions History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        account.showTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        account.withdraw(withdrawalAmount);
                        break;
                    case 3:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientId = scanner.nextLine();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine(); 

                        
                        Account recipientAccount = new Account("recipient123", "5678", 0.0);
                        if (recipientAccount.getUserId().equals(recipientId)) {
                            account.transfer(recipientAccount, transferAmount);
                        } else {
                            System.out.println("Recipient User ID not found.");
                        }
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Thank you for using the ATM.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Authentication failed. User ID or PIN is incorrect.");
        }
        scanner.close();
    }
}
