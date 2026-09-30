package com.ems.util;

import com.ems.exception.InvalidEmployeeDataException;

public final class InputValidator {
    private InputValidator() {
    }

    public static int parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            throw new InvalidEmployeeDataException(fieldName + " must be a whole number.", exception);
        }
    }

    public static double parseNonNegativeNumber(String value, String fieldName) {
        try {
            double number = Double.parseDouble(value.trim());
            if (!Double.isFinite(number) || number < 0) {
                throw new InvalidEmployeeDataException(fieldName + " must be a non-negative number.");
            }
            return number;
        } catch (NumberFormatException exception) {
            throw new InvalidEmployeeDataException(fieldName + " must be a valid number.", exception);
        }
    }

    public static int validateEmployeeId(int employeeId) {
        if (employeeId <= 0) {
            throw new InvalidEmployeeDataException("Employee ID must be greater than zero.");
        }
        return employeeId;
    }

    public static String validateName(String name) {
        return validateRequiredText(name, "Name");
    }

    public static String validateDepartment(String department) {
        return validateRequiredText(department, "Department");
    }

    public static String validateEmail(String email) {
        String value = validateRequiredText(email, "Email");
        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new InvalidEmployeeDataException("Email must be in a valid format, such as name@example.com.");
        }
        return value;
    }

    public static double validateNonNegative(double value, String fieldName) {
        if (!Double.isFinite(value) || value < 0) {
            throw new InvalidEmployeeDataException(fieldName + " must be a non-negative number.");
        }
        return value;
    }

    public static int validatePositiveInteger(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidEmployeeDataException(fieldName + " must be greater than zero.");
        }
        return value;
    }

    private static String validateRequiredText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidEmployeeDataException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}