CREATE DATABASE UniversityDB;

USE UniversityDB;

-- Create tables for departments, teachers, courses, students, enrollments, etc.

CREATE TABLE Departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Departments(id)
);

CREATE TABLE Students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    department_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    hostel_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(id)
);

CREATE TABLE Courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Departments(id),
    FOREIGN KEY (teacher_id) REFERENCES Teachers(id)
);

CREATE TABLE Enrollments (
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (course_id) REFERENCES Courses(id)
);

CREATE TABLE Exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(id),
    FOREIGN KEY (teacher_id) REFERENCES Teachers(id)
);

CREATE TABLE ExamResults (
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (exam_id, student_id),
    FOREIGN KEY (exam_id) REFERENCES Exams(id),
    FOREIGN KEY (student_id) REFERENCES Students(id)
);

CREATE TABLE Quizzes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(id),
    FOREIGN KEY (teacher_id) REFERENCES Teachers(id)
);

CREATE TABLE QuizResults (
    quiz_id INT NOT NULL,
    student_id INT NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (quiz_id, student_id),
    FOREIGN KEY (quiz_id) REFERENCES Quizzes(id),
    FOREIGN KEY (student_id) REFERENCES Students(id)
);

CREATE TABLE Libraries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    library_id INT NOT NULL,
    FOREIGN KEY (library_id) REFERENCES Libraries(id)
);

CREATE TABLE BorrowedBooks (
    student_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    PRIMARY KEY (student_id, book_id),
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (book_id) REFERENCES Books(id)
);

CREATE TABLE Hostels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Club (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE ClubMemberships (
    student_id INT NOT NULL,
    club_id INT NOT NULL,
    PRIMARY KEY (student_id, club_id),
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (club_id) REFERENCES Club(id)
);

-- Create Admin manually in the database

INSERT INTO Departments (name) VALUES ('Computer Science');
INSERT INTO Teachers (name, department_id, password) VALUES ('admin', 1, 'adminpassword');
