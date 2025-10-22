import java.sql.*;
import java.util.*;

public class PayrollSystem {
    private final Connection conn;
    private final Scanner sc;

    PayrollSystem(String filePath) {
        this.conn = new DatabaseConnection(filePath).getConnection();
        this.sc = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to Payroll System");

        boolean runnning = true;

        while (runnning) {
            System.out.println("\n1 - Update employee salary");
            System.out.println("2 - View all employees");
            System.out.println("3 - Calculate total payroll");
            System.out.println("4 - Exit");
            System.out.print("Your choice (1-4): ");

            switch (sc.nextLine()) {
                case "1" -> {
                    System.out.print("Id:");
                    updateSalary(sc.nextInt());
                }
                case "2" -> viewAllEmployees();
                case "3" -> calculateTotalPayroll();
                case "4" -> {
                    System.out.println("Exiting...");
                    sc.close();
                    runnning = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1-4.");
            }

        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sqlText = "SELECT * FROM employees;";

        try (Statement stmt = conn.createStatement()) {
            ResultSet res = stmt.executeQuery(sqlText);

            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String position = res.getString("position");
                double salary = res.getDouble("salary");

                Employee employee = new Employee(id, name, position, salary);
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving employees: " + e.getMessage());
        }
        return employees;
    }

    public void viewAllEmployees() {
        List<Employee> employees = getAllEmployees();
        for (Employee e : employees) {
            System.out.println(e);
        }

    }

    public void updateSalary(int id) {
        String sqlText = "UPDATE employees SET salary = ? WHERE id = ?";
        System.out.print("What is the new salary? : ");
        if (sc.hasNextDouble()) {
            double newSalary = sc.nextDouble();
            sc.nextLine(); // clear leftover newline

            try (PreparedStatement ps = conn.prepareStatement(sqlText)) {
                ps.setDouble(1, newSalary);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Updated salary for employee with ID: " + id);
                } else {
                    System.out.println("No employee found with ID: " + id);
                }

            } catch (SQLException e) {
                System.out.println("Error updating salary: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid input. Please enter a numeric salary.");
            sc.nextLine(); // discard invalid input
        }
    }

    public void calculateTotalPayroll() {
        String sql = "SELECT SUM(salary) AS total_payroll FROM employees";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                double totalPayroll = rs.getDouble("total_payroll");
                System.out.printf("Total payroll: $%.2f%n", totalPayroll);
            } else {
                System.out.println("Could not calculate total payroll (no data).");
            }

        } catch (SQLException e) {
            System.out.println("Error calculating total payroll: " + e.getMessage());
        }
    }
}
