# Student Management System

A standalone Core Java console application for adding, viewing, searching, and deleting student records, and calculating grades and pass/fail results.

## Requirements

- Java Development Kit (JDK)

## Build and Run

From the repository root:

```powershell
Set-Location project01
javac -d out src\Main.java src\Student.java src\StudentManager.java
java -cp out Main
```

The project has no external dependencies. Build output is kept in `out/` and ignored by Git.