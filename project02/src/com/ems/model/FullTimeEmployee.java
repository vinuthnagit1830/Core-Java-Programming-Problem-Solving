package com.ems.model;

import com.ems.util.InputValidator;

public class FullTimeEmployee extends Employee {
    private double monthlySalary;
    private double bonus;

    public FullTimeEmployee(int employeeId, String name, String department, String email,
                            double monthlySalary, double bonus) {
        super(employeeId, name, department, email);
        setMonthlySalary(monthlySalary);
        setBonus(bonus);
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = InputValidator.validateNonNegative(monthlySalary, "Monthly salary");
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = InputValidator.validateNonNegative(bonus, "Bonus");
    }

    @Override
    public double calculateSalary() {
        return monthlySalary + bonus;
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
}