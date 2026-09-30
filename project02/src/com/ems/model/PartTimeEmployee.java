package com.ems.model;

import com.ems.util.InputValidator;

public class PartTimeEmployee extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int employeeId, String name, String department, String email,
                            double hoursWorked, double hourlyRate) {
        super(employeeId, name, department, email);
        setHoursWorked(hoursWorked);
        setHourlyRate(hourlyRate);
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = InputValidator.validateNonNegative(hoursWorked, "Hours worked");
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = InputValidator.validateNonNegative(hourlyRate, "Hourly rate");
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }
}