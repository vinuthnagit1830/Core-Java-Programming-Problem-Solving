package com.ems.main;

import com.ems.exception.EmployeeNotFoundException;
import com.ems.exception.InvalidEmployeeDataException;
import com.ems.model.Employee;
import com.ems.model.FullTimeEmployee;
import com.ems.model.Intern;
import com.ems.model.PartTimeEmployee;
import com.ems.service.EmployeeService;
import com.ems.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String APPLICATION_NAME = "Employee Management System";
    private static final String SEPARATOR = "==============================================";
    private static final EmployeeService EMPLOYEE_SERVICE = new EmployeeService();

    private Main() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(SEPARATOR);
            System.out.println("Welcome to " + APPLICATION_NAME);
            System.out.println(SEPARATOR);

            boolean running = true;
            while (running) {
                displayMenu();
                int choice = readInteger(scanner, "Enter your choice: ");
                try {
                    switch (choice) {
                        case 1 -> addEmployee(scanner);
                        case 2 -> viewAllEmployees();
                        case 3 -> searchEmployee(scanner);
                        case 4 -> updateEmployee(scanner);
                        case 5 -> deleteEmployee(scanner);
                        case 6 -> calculateSalary(scanner);
                        case 7 -> displayEmployeeDetails(scanner);
                        case 8 -> {
                            System.out.println("Thank you for using " + APPLICATION_NAME + ". Goodbye!");
                            running = false;
                        }
                        default -> System.out.println("[ERROR] Choose a menu option from 1 to 8.");
                    }
                } catch (EmployeeNotFoundException | InvalidEmployeeDataException exception) {
                    System.out.println("[ERROR] " + exception.getMessage());
                } finally {
                    System.out.println();
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println(SEPARATOR);
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Calculate Salary");
        System.out.println("7. Display Employee Details");
        System.out.println("8. Exit");
    }

    private static void addEmployee(Scanner scanner) {
        System.out.println("Select Employee Type:");
        System.out.println("1. Full-Time Employee");
        System.out.println("2. Part-Time Employee");
        System.out.println("3. Intern");
        int type = readInteger(scanner, "Enter type: ");
        if (type < 1 || type > 3) {
            System.out.println("[ERROR] Choose an employee type from 1 to 3.");
            return;
        }

        int employeeId = InputValidator.validateEmployeeId(readInteger(scanner, "Employee ID: "));
        String name = readRequiredText(scanner, "Name: ", "Name");
        String department = readRequiredText(scanner, "Department: ", "Department");
        String email = readEmail(scanner);

        Employee employee = switch (type) {
            case 1 -> new FullTimeEmployee(employeeId, name, department, email,
                    readNonNegativeNumber(scanner, "Monthly salary: "),
                    readNonNegativeNumber(scanner, "Bonus: "));
            case 2 -> new PartTimeEmployee(employeeId, name, department, email,
                    readNonNegativeNumber(scanner, "Hours worked: "),
                    readNonNegativeNumber(scanner, "Hourly rate: "));
            case 3 -> new Intern(employeeId, name, department, email,
                    readNonNegativeNumber(scanner, "Stipend: "),
                    readPositiveInteger(scanner, "Duration in months: "));
            default -> throw new IllegalStateException("Validated employee type was out of range.");
        };

        EMPLOYEE_SERVICE.addEmployee(employee);
        System.out.println("[SUCCESS] Employee added successfully.");
    }

    private static void viewAllEmployees() {
        List<Employee> employees = EMPLOYEE_SERVICE.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("[INFO] No employees found.");
            return;
        }
        employees.forEach(Main::printEmployee);
    }

    private static void searchEmployee(Scanner scanner) {
        System.out.println("Search by: 1. Employee ID  2. Employee Name");
        int searchChoice = readInteger(scanner, "Enter search method: ");
        if (searchChoice == 1) {
            printEmployee(EMPLOYEE_SERVICE.findEmployeeById(readInteger(scanner, "Employee ID: ")));
        } else if (searchChoice == 2) {
            List<Employee> matches = EMPLOYEE_SERVICE.searchEmployeeByName(
                    readRequiredText(scanner, "Employee name: ", "Name"));
            if (matches.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found matching that name.");
            }
            matches.forEach(Main::printEmployee);
        } else {
            System.out.println("[ERROR] Choose search method 1 or 2.");
        }
    }

    private static void updateEmployee(Scanner scanner) {
        int employeeId = readInteger(scanner, "Employee ID to update: ");
        String name = readRequiredText(scanner, "New name: ", "Name");
        String department = readRequiredText(scanner, "New department: ", "Department");
        String email = readEmail(scanner);
        EMPLOYEE_SERVICE.updateEmployee(employeeId, name, department, email);
        System.out.println("[SUCCESS] Employee updated successfully.");
    }

    private static void deleteEmployee(Scanner scanner) {
        int employeeId = readInteger(scanner, "Employee ID to delete: ");
        Employee employee = EMPLOYEE_SERVICE.findEmployeeById(employeeId);
        System.out.print("Delete " + employee.getName() + "? (Y/N): ");
        String confirmation = scanner.nextLine().trim();
        if (confirmation.equalsIgnoreCase("Y")) {
            EMPLOYEE_SERVICE.deleteEmployee(employeeId);
            System.out.println("[SUCCESS] Employee deleted successfully.");
        } else {
            System.out.println("[INFO] Deletion cancelled.");
        }
    }

    private static void calculateSalary(Scanner scanner) {
        int employeeId = readInteger(scanner, "Employee ID: ");
        double salary = EMPLOYEE_SERVICE.calculateSalary(employeeId);
        System.out.printf(java.util.Locale.US, "[INFO] Calculated salary: %.2f%n", salary);
    }

    private static void displayEmployeeDetails(Scanner scanner) {
        printEmployee(EMPLOYEE_SERVICE.findEmployeeById(readInteger(scanner, "Employee ID: ")));
    }

    private static void printEmployee(Employee employee) {
        System.out.println("----------------------------------------------");
        System.out.println(employee);
    }

    private static int readInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return InputValidator.parseInteger(scanner.nextLine(), "Input");
            } catch (InvalidEmployeeDataException exception) {
                System.out.println("[ERROR] Invalid input. Please enter a valid whole number.");
            }
        }
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        while (true) {
            int value = readInteger(scanner, prompt);
            try {
                return InputValidator.validatePositiveInteger(value, prompt.replace(": ", ""));
            } catch (InvalidEmployeeDataException exception) {
                System.out.println("[ERROR] " + exception.getMessage());
            }
        }
    }

    private static double readNonNegativeNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return InputValidator.parseNonNegativeNumber(scanner.nextLine(), prompt.replace(": ", ""));
            } catch (InvalidEmployeeDataException exception) {
                System.out.println("[ERROR] " + exception.getMessage());
            }
        }
    }

    private static String readRequiredText(Scanner scanner, String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            try {
                return switch (fieldName) {
                    case "Name" -> InputValidator.validateName(value);
                    case "Department" -> InputValidator.validateDepartment(value);
                    default -> throw new IllegalArgumentException("Unsupported text field.");
                };
            } catch (InvalidEmployeeDataException exception) {
                System.out.println("[ERROR] " + exception.getMessage());
            }
        }
    }

    private static String readEmail(Scanner scanner) {
        while (true) {
            System.out.print("Email: ");
            try {
                return InputValidator.validateEmail(scanner.nextLine());
            } catch (InvalidEmployeeDataException exception) {
                System.out.println("[ERROR] " + exception.getMessage());
            }
        }
    }
}