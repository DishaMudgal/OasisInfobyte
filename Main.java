import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
    public String toString() {
        return "Type: " + type + ", Amount: " + amount;
    }
}

class Account {
    private double balance;
    private Transaction[] transactions;
    private int transactionCount;

    public Account() {
        this.balance = 0;
        this.transactions = new Transaction[100];
        this.transactionCount = 0;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount < 0) {
            System.out.println("Invalid deposit amount");
            return false;
        }
        balance += amount;
        addTransaction(new Transaction("Deposit", amount));
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount < 0 || amount > balance) {
            System.out.println("Invalid withdrawal amount or insufficient funds");
            return false;
        }
        balance -= amount;
        addTransaction(new Transaction("Withdrawal", amount));
        return true;
    }

    public boolean transfer(double amount, Account recipient) {
        if (withdraw(amount)) {
            return recipient.deposit(amount);
        }
        return false;
    }

    private void addTransaction(Transaction transaction) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount++] = transaction;
        }
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactionCount == 0) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println(transactions[i]);
            }
        }
    }
}

class User {
    private int userId;
    private int userPin;
    private Account account;

    public User(int userId, int userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.account = new Account();
    }

    public boolean authenticate(int enteredPin) {
        return enteredPin == userPin;
    }

    public int getUserId() {
        return userId;
    }

    public Account getAccount() {
        return account;
    }
}

class Bank {
    private User[] users;
    private int userCount;

    public Bank() {
        this.users = new User[10]; // Maximum of 10 users for simplicity
        this.userCount = 0;
    }

    public void addUser(User user) {
        if (userCount < users.length) {
            users[userCount++] = user;
        }
    }

    public User getUserById(int userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUserId() == userId) {
                return users[i];
            }
        }
        return null;
    }
}

public class Main {
    private static Bank bank = new Bank();

    public static void main(String[] args) {
       
        User user1 = new User(1, 1234);
        User user2 = new User(2, 5678);

       
        bank.addUser(user1);
        bank.addUser(user2);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        User currentUser = bank.getUserById(userId);

        if (currentUser != null) {
            System.out.print("Enter PIN: ");
            int enteredPin = scanner.nextInt();

            if (currentUser.authenticate(enteredPin)) {
                showMenu(currentUser);
            } else {
                System.out.println("Incorrect PIN. Exiting...");
            }
        } else {
            System.out.println("User not found. Exiting...");
        }
    }

    private static void showMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    user.getAccount().displayTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount:");
                    double withdrawalAmount = scanner.nextDouble();
                    if (user.getAccount().withdraw(withdrawalAmount)) {
                        System.out.println("Withdrawal successful");
                    } else {
                        System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
                    }
                    break;
                case 3:
                    System.out.print("Enter deposit amount:");
                    double depositAmount = scanner.nextDouble();
                    if (user.getAccount().deposit(depositAmount)) {
                        System.out.println("Deposit successful");
                    } else {
                        System.out.println("Invalid deposit amount.");
                    }
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    int recipientId = scanner.nextInt();
                    User recipient = bank.getUserById(recipientId);

                    if (recipient != null) {
                        System.out.print("Enter transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        if (user.getAccount().transfer(transferAmount, recipient.getAccount())) {
                            System.out.println("Transfer successful");
                        } else {
                            System.out.println("Transfer failed. Insufficient funds or invalid amount.");
                        }
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);
    }
}
