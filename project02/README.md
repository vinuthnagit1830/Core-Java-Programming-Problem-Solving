# Employee Management System

A menu-driven Java console application for managing full-time employees, part-time employees, and interns. It uses an in-memory collection, so no database or external dependency is required.

## Features

- Add, list, search, update, and delete employees
- Calculate salaries according to employee type
- Search by ID or partial employee name
- Validate user input and report errors without terminating the application
- Optional sample-data loader in `EmployeeService`

## Technologies Used

- Java 17 or later
- JDK command-line tools, including `javac` and `java`
- Java Collections Framework and `Scanner`

## Requirements

- JDK 17 or later installed and available on `PATH`
- VS Code (optional; any terminal can compile and run the application)

## Project Structure

```text
EmployeeManagementSystem/
|-- src/
|   `-- com/ems/
|       |-- exception/
|       |-- interfaces/
|       |-- main/
|       |-- model/
|       |-- service/
|       `-- util/
|-- .gitignore
`-- README.md
```

`interfaces` is used because `interface` is a reserved Java keyword and cannot be a package identifier.

## How to Run

Open a VS Code terminal in the `EmployeeManagementSystem` directory.

PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out com.ems.main.Main
```

Windows Command Prompt:

```cmd
for /R src %f in (*.java) do @echo %f >> sources.txt
javac -d out @sources.txt
java -cp out com.ems.main.Main
del sources.txt
```

In a `.bat` file, use `%%f` instead of `%f` in the `for` command. To run from VS Code's Java Run button, install the Java extensions, open `src/com/ems/main/Main.java`, and select **Run** above `main`.

## How to Use

Select a menu option and follow the prompts. IDs must be positive and unique; salary, bonus, hours, rate, and stipend must be non-negative. Employee name, department, and a basic valid email address are required. Employee records remain in memory only and are cleared when the program exits.

## OOP Concepts Demonstrated

- **Classes and Objects:** `EmployeeService` creates and manages objects of the employee classes.
- **Constructors:** Each concrete employee constructor initializes its own pay fields and calls `super(...)` to initialize shared employee data.
- **Encapsulation:** Employee and pay fields are private; access is provided through getters and validating setters.
- **Inheritance:** `FullTimeEmployee`, `PartTimeEmployee`, and `Intern` extend the abstract `Employee` class.
- **Abstraction:** `Employee` defines shared employee state and leaves salary calculation and employee type to concrete implementations.
- **Interfaces:** `Employee` implements `SalaryCalculable`, which defines the salary calculation contract used by the service and UI.
- **Polymorphism:** The `List<Employee>` holds all three concrete types. Calls to `calculateSalary()` dispatch to each object's implementation at runtime.
- **Exception Handling:** Custom runtime exceptions report invalid data and missing employees; the menu catches these errors and continues. Parsing errors are handled at the input prompt.
- **Packages:** Main, models, service, validation, interface, and exception responsibilities are organized under `com.ems`.
- **`equals` and `hashCode`:** `Employee` bases equality on its immutable unique ID so employees can be compared consistently.

## Exception Handling

`InvalidEmployeeDataException` represents invalid employee input, including duplicate IDs. `EmployeeNotFoundException` is used when an ID lookup fails. Input parsing retries on invalid values, and operation errors return control to the menu.

## Collections Used

`EmployeeService` stores employees in an `ArrayList<Employee>`, returns immutable snapshots for display, and uses stream operations to search records. The common `Employee` type allows every employee subtype to be managed through one collection.

## Clean Code Practices

- UI input/output stays in `Main`; employee management rules stay in `EmployeeService`.
- Shared validation is centralized in `InputValidator` and repeated field validation is also enforced by setters.
- Fields are private, IDs are immutable, and methods use descriptive names.
- Constants are used for the application title and menu separator.

## Sample Output

```text
==============================================
Welcome to Employee Management System
==============================================
1. Add Employee
2. View All Employees
3. Search Employee
4. Update Employee
5. Delete Employee
6. Calculate Salary
7. Display Employee Details
8. Exit
Enter your choice: 8
Thank you for using Employee Management System. Goodbye!
```

## Manual Testing

Run the program and verify these flows:

1. Add one full-time employee, one part-time employee, and one intern.
2. View all employees and confirm each type and salary is displayed.
3. Search by an existing ID and by a partial employee name.
4. Search for a nonexistent ID and confirm the error does not exit the program.
5. Update an employee's name, department, and email; verify the new details.
6. Delete an employee, confirm with `Y`, then verify it is absent. Also cancel with `N`.
7. Attempt a duplicate ID and confirm it is rejected.
8. Enter a negative salary and confirm the prompt requests a valid non-negative value.
9. Enter text at a numeric menu prompt and confirm it retries; enter an out-of-range menu choice.
10. Enter an invalid email and an empty name or department and confirm validation retries.
11. Calculate salary for each employee type and compare to the configured pay inputs.
12. Exit with option `8` and confirm normal termination.

## Future Enhancements

- Persist employee records to a file
- Add sorting and department filters
- Add automated unit tests

## Author

Developed as an internship assignment project.