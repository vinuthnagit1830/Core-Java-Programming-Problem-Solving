package com.ems.model;

import com.ems.util.InputValidator;

public class Intern extends Employee {
    private double stipend;
    private int durationInMonths;

    public Intern(int employeeId, String name, String department, String email,
                  double stipend, int durationInMonths) {
        super(employeeId, name, department, email);
        setStipend(stipend);
        setDurationInMonths(durationInMonths);
    }

    public double getStipend() {
        return stipend;
    }

    public void setStipend(double stipend) {
        this.stipend = InputValidator.validateNonNegative(stipend, "Stipend");
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(int durationInMonths) {
        this.durationInMonths = InputValidator.validatePositiveInteger(durationInMonths, "Duration in months");
    }

    @Override
    public double calculateSalary() {
        return stipend;
    }

    @Override
    public String getEmployeeType() {
        return "Intern";
    }
}