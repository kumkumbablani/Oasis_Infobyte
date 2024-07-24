package out.production.OASIS;

import java.util.ArrayList;
import java.util.Scanner;

class ATM_Interface {
    final private static ArrayList<String> transactionsHistory = new ArrayList<>();
    private static int balance = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Greetings User... Welcome to the ATM ");
        System.out.print("Enter your User ID: ");
        sc.nextLine();
        System.out.print(" Enter your User PIN: ");
        sc.nextLine();

        System.out.println("You are logged in Successfully!");
        mainMenu();
    }

    private static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Main Menu:");
            System.out.println("1.Transactions History");
            System.out.println("2.Withdraw");
            System.out.println("3.Deposit");
            System.out.println("4.Transfer");
            System.out.println("5.Quit");
            System.out.print("Choose your action: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    tranHistory();
                    break;
                case 2:
                    withdrawal();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM! Have a great day ahead!!");
                    break;
                default:
                    System.out.println("Please enter a valid option.");
            }
        } while (choice != 5);
    }

    private static void tranHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionsHistory) {
            System.out.println(transaction);
        }
    }

    private static void withdrawal() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        int amount = sc.nextInt();

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionsHistory.add("Withdraw: " + amount);
            System.out.println("Withdrawal successful! Current balance: " + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private static void deposit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        int amount = sc.nextInt();

        if (amount > 0) {
            balance += amount;
            transactionsHistory.add("Deposit: " + amount);
            System.out.println("Deposited successfully. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private static void transfer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter recipient's account number: ");
        String recipientAccount = sc.nextLine();

        System.out.print("Enter amount to transfer: ");
        int amount = sc.nextInt();

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionsHistory.add("Transfer: " + amount + " to " + recipientAccount);
            System.out.println("Transfer successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }
}