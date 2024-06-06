import java.util.Scanner;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the ATM. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void checkBalance() {
        System.out.printf("Your current balance is: $%.2f\n", account.getBalance());
    }

    private void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = getAmount();
        if (account.deposit(amount)) {
            System.out.printf("$%.2f has been deposited to your account.\n", amount);
        } else {
            System.out.println("Invalid amount. Deposit failed.");
        }
    }

    private void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = getAmount();
        if (account.withdraw(amount)) {
            System.out.printf("$%.2f has been withdrawn from your account.\n", amount);
        } else {
            System.out.println("Invalid amount or insufficient balance. Withdrawal failed.");
        }
    }

    private double getAmount() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid amount: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}

public class ATMApplication {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.00); // Initial balance of $1000
        ATM atm = new ATM(account);
        atm.start();
    }
}
