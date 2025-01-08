-- Create the UniversityDB database
CREATE DATABASE UniversityDB;

USE UniversityDB;

-- Create the Departments table
CREATE TABLE Departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create the Teachers table with a role column
CREATE TABLE Teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'teacher',  -- Role column added here
    FOREIGN KEY (department_id) REFERENCES Departments(id)
);

-- Create the Students table with a role column
CREATE TABLE Students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    department_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    hostel_id INT,
    role VARCHAR(50) NOT NULL DEFAULT 'student',  -- Role column added here
    FOREIGN KEY (department_id) REFERENCES Departments(id)
);

-- Create other tables like Courses, Enrollments, Exams, etc., as before

-- Create the Enrollments table
CREATE TABLE Enrollments (
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (course_id) REFERENCES Courses(id)
);

-- Create the Exams table
CREATE TABLE Exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(id),
    FOREIGN KEY (teacher_id) REFERENCES Teachers(id)
);

-- Create the ExamResults table
CREATE TABLE ExamResults (
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (exam_id, student_id),
    FOREIGN KEY (exam_id) REFERENCES Exams(id),
    FOREIGN KEY (student_id) REFERENCES Students(id)
);

-- Create the Quizzes table
CREATE TABLE Quizzes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(id),
    FOREIGN KEY (teacher_id) REFERENCES Teachers(id)
);

-- Create the QuizResults table
CREATE TABLE QuizResults (
    quiz_id INT NOT NULL,
    student_id INT NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (quiz_id, student_id),
    FOREIGN KEY (quiz_id) REFERENCES Quizzes(id),
    FOREIGN KEY (student_id) REFERENCES Students(id)
);

-- Create the Libraries table
CREATE TABLE Libraries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create the Books table
CREATE TABLE Books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    library_id INT NOT NULL,
    FOREIGN KEY (library_id) REFERENCES Libraries(id)
);

-- Create the BorrowedBooks table
CREATE TABLE BorrowedBooks (
    student_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    PRIMARY KEY (student_id, book_id),
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (book_id) REFERENCES Books(id)
);

-- Create the Hostels table
CREATE TABLE Hostels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create the Club table
CREATE TABLE Club (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create the ClubMemberships table
CREATE TABLE ClubMemberships (
    student_id INT NOT NULL,
    club_id INT NOT NULL,
    PRIMARY KEY (student_id, club_id),
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (club_id) REFERENCES Club(id)
);

---- Create the Admin table to store admin-specific data
CREATE TABLE Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'admin' -- Ensures the role is always admin
);
-- Insert admin user into the Admin table
INSERT INTO Admin (username, password, role)
VALUES ('admin', 'adminpassword', 'admin');

ALTER TABLE Hostels
ADD COLUMN capacity INT;
