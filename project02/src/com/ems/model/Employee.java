package com.ems.model;

import com.ems.interfaces.SalaryCalculable;
import com.ems.util.InputValidator;

import java.util.Objects;

public abstract class Employee implements SalaryCalculable {
    private final int employeeId;
    private String name;
    private String department;
    private String email;

    protected Employee(int employeeId, String name, String department, String email) {
        this.employeeId = InputValidator.validateEmployeeId(employeeId);
        setName(name);
        setDepartment(department);
        setEmail(email);
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = InputValidator.validateName(name);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = InputValidator.validateDepartment(department);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = InputValidator.validateEmail(email);
    }

    public abstract String getEmployeeType();

    @Override
    public String toString() {
        return "Employee ID : " + employeeId + System.lineSeparator()
                + "Name        : " + name + System.lineSeparator()
                + "Department  : " + department + System.lineSeparator()
                + "Email       : " + email + System.lineSeparator()
                + "Type        : " + getEmployeeType() + System.lineSeparator()
                + String.format(java.util.Locale.US, "Salary      : %.2f", calculateSalary());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Employee employee)) {
            return false;
        }
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}