import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExpenseTracker {
    static Scanner sc = new Scanner(System.in);
    static Map<String, List<Expense>> userExpenses = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Expense Tracker ");
        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1 : registerUser();
                		break;
                case 2 : loginUser();
                		break;
                case 3 : 
                    System.out.println("Thank you for using Expense Tracker ");
                    System.exit(0);
                    break;
                default :System.out.println("Invalid choice. Please try again ");
            }
        }
    }

    static void registerUser() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (userExpenses.containsKey(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }
        userExpenses.put(username, new ArrayList<>());
        System.out.println("Registration successful ");
    }

    static void loginUser() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (!userExpenses.containsKey(username)) {
            System.out.println("User not found. Please register first.");
            return;
        }
        System.out.println("Login successful ");
        userMenu(username);
    }

    static void userMenu(String username) {
        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Category-wise Total");
            System.out.println("4. Save & Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1 : addExpense(username);
                		break;
                case 2 : viewExpenses(username);
                		break;
                case 3 : viewCategoryWiseTotal(username);
                		break;
                case 4 : 
                    saveExpenses(username);
                    System.out.println("Logged out successfully.");
                    return;
                    
                default : System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addExpense(String username) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter category: ");
        String category = sc.nextLine();
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        userExpenses.get(username).add(new Expense(date, category, amount));
        System.out.println("Expense added successfully ");
    }

    static void viewExpenses(String username) {
        List<Expense> expenses = userExpenses.get(username);
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("\nDate       | Category   | Amount");
        System.out.println("-------------------------------");
        for (Expense expense : expenses) {
            System.out.printf("%s | %-10s | %.2f\n", expense.date, expense.category, expense.amount);
        }
    }

    static void viewCategoryWiseTotal(String username) {
        List<Expense> expenses = userExpenses.get(username);
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.put(expense.category, categoryTotals.getOrDefault(expense.category, 0.0) + expense.amount);
        }
        System.out.println("\nCategory-wise Totals:");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.printf("%s: %.2f\n", entry.getKey(), entry.getValue());
        }
    }

    static void saveExpenses(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_expenses.txt"))) {
            for (Expense expense : userExpenses.get(username)) {
                writer.write(expense.date + "," + expense.category + "," + expense.amount);
                writer.newLine();
            }
            System.out.println("Expenses saved successfully ");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }
}

class Expense {
    String date;
    String category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
    
}
