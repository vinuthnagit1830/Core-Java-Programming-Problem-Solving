package com.ems.service;

import com.ems.exception.EmployeeNotFoundException;
import com.ems.exception.InvalidEmployeeDataException;
import com.ems.model.Employee;
import com.ems.model.FullTimeEmployee;
import com.ems.model.Intern;
import com.ems.model.PartTimeEmployee;
import com.ems.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new InvalidEmployeeDataException("Employee cannot be null.");
        }
        boolean duplicateId = employees.stream()
                .anyMatch(existing -> existing.getEmployeeId() == employee.getEmployeeId());
        if (duplicateId) {
            throw new InvalidEmployeeDataException("An employee with ID " + employee.getEmployeeId() + " already exists.");
        }
        employees.add(employee);
    }

    public List<Employee> getAllEmployees() {
        return List.copyOf(employees);
    }

    public Employee findEmployeeById(int employeeId) {
        InputValidator.validateEmployeeId(employeeId);
        return employees.stream()
                .filter(employee -> employee.getEmployeeId() == employeeId)
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));
    }

    public List<Employee> searchEmployeeByName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidEmployeeDataException("Search name cannot be empty.");
        }
        String searchTerm = name.trim().toLowerCase(java.util.Locale.ROOT);
        return employees.stream()
                .filter(employee -> employee.getName().toLowerCase(java.util.Locale.ROOT).contains(searchTerm))
                .toList();
    }

    public void updateEmployee(int employeeId, String name, String department, String email) {
        String validName = InputValidator.validateName(name);
        String validDepartment = InputValidator.validateDepartment(department);
        String validEmail = InputValidator.validateEmail(email);
        Employee employee = findEmployeeById(employeeId);
        employee.setName(validName);
        employee.setDepartment(validDepartment);
        employee.setEmail(validEmail);
    }

    public void deleteEmployee(int employeeId) {
        employees.remove(findEmployeeById(employeeId));
    }

    public double calculateSalary(int employeeId) {
        return findEmployeeById(employeeId).calculateSalary();
    }

    public void loadSampleEmployees() {
        addEmployee(new FullTimeEmployee(101, "Anjali", "IT", "anjali@example.com", 45000, 5000));
        addEmployee(new PartTimeEmployee(102, "Ravi", "HR", "ravi@example.com", 80, 250));
        addEmployee(new Intern(103, "Sneha", "CSE", "sneha@example.com", 12000, 6));
    }
}