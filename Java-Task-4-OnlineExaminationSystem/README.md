
@'
# Online Examination System

## Project Overview
A Java Swing-based Online Examination System that allows students to log in, answer multiple-choice questions, submit an exam, and view their results.

## Features
- Student login interface
- Multiple-choice examination questions
- Previous and Next navigation
- Exam timer
- Automatic score calculation
- Examination result screen
- MySQL database integration using JDBC
- Saves student name, score, total questions, and exam date

## Technologies Used
- Java
- Java Swing
- MySQL
- JDBC
- MySQL Connector/J

## Project Structure
```text
Java-Task-4-OnlineExaminationSystem/
├── Main.java
├── LoginFrame.java
├── ExamFrame.java
├── Question.java
├── ExamManager.java
├── ResultFrame.java
├── DBConnection.java
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── screenshots/
└── README.md
```

## Database Setup
Create the database and select it in MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS online_reservation_db;
USE online_reservation_db;
```

The application uses the `exam_results` table to store exam results. Ensure the table exists with these columns:
- `id`
- `student_name`
- `score`
- `total_questions`
- `exam_date`

## Configuration
Set the MySQL password using the PowerShell environment variable:

```powershell
$env:DB_PASSWORD = Read-Host "Enter MySQL password"
```

Do not store database passwords in source code or upload them to GitHub.

## Compile and Run
Run these commands from the project folder:

```powershell
javac -cp ".;lib\mysql-connector-j-26.7.0.jar" *.java
java -cp ".;lib\mysql-connector-j-26.7.0.jar" Main
```

## Screenshots
Project screenshots are available in the `screenshots` folder.

## Testing
- Student login tested successfully
- Exam submission and result display tested
- Exam result saved and verified in MySQL Workbench

## Author
Jeya R

## Internship
Java Development Internship - Oasis Infobyte
'@ | Set-Content README.md
