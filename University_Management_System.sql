CREATE DATABASE University_Management_System;
USE University_Management_System;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Student', 'Faculty', 'IT_Officer') NOT NULL
);
CREATE TABLE departments (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    head_of_department INT
);
CREATE TABLE faculty (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    department_id INT NOT NULL,
    position VARCHAR(50),
    hire_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);
ALTER TABLE departments ADD FOREIGN KEY (head_of_department) REFERENCES faculty(faculty_id);


ALTER TABLE faculty
ADD COLUMN first_name VARCHAR(50) NOT NULL,
ADD COLUMN last_name VARCHAR(50) NOT NULL,
ADD COLUMN email VARCHAR(100) UNIQUE NOT NULL;



ALTER TABLE students
ADD COLUMN first_name VARCHAR(50) NOT NULL,
ADD COLUMN last_name VARCHAR(50) NOT NULL,
ADD COLUMN email VARCHAR(100) UNIQUE NOT NULL;


ALTER TABLE faculty
ADD COLUMN first_name VARCHAR(50) NOT NULL,
ADD COLUMN last_name VARCHAR(50) NOT NULL,
ADD COLUMN email VARCHAR(100) UNIQUE NOT NULL;


ALTER TABLE departments
ADD COLUMN floor INT NOT NULL;


CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    department_id INT NOT NULL,
    credits INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);


CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    faculty_id INT NOT NULL,
    course_id INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);


CREATE TABLE enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE teacher_courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE student_grades (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade CHAR(2) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE examinations (
    exam_id INT AUTO_INCREMENT PRIMARY KEY,
    exam_name VARCHAR(100) NOT NULL,
    exam_date DATE NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);

CREATE TABLE exam_results (
    result_id INT AUTO_INCREMENT PRIMARY KEY,
    exam_id INT NOT NULL,
    student_id INT NOT NULL,
    marks INT NOT NULL,
    grade VARCHAR(5),
    FOREIGN KEY (exam_id) REFERENCES examinations(exam_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);



CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    description TEXT
);

CREATE TABLE student_events (
    student_id INT NOT NULL,
    event_id INT NOT NULL,
    registration_date DATE NOT NULL,
    PRIMARY KEY (student_id, event_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);

CREATE TABLE faculty_events (
    faculty_id INT NOT NULL,
    event_id INT NOT NULL,
    registration_date DATE NOT NULL,
    PRIMARY KEY (faculty_id, event_id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);

CREATE TABLE university_events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    department_id INT,
    location VARCHAR(100),
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);


CREATE TABLE libraries (
    library_id INT AUTO_INCREMENT PRIMARY KEY,
    library_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL
);

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100),
    isbn VARCHAR(20) UNIQUE NOT NULL,
    library_id INT NOT NULL,
    FOREIGN KEY (library_id) REFERENCES libraries(library_id)
);

CREATE TABLE library_management (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    student_id INT NOT NULL,
    issue_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

CREATE TABLE hostels (
    hostel_id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE hostel_management (
    hostel_allocation_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    hostel_id INT NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    allocation_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id)
);

CREATE TABLE alumni (
    alumni_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    graduation_year YEAR,
    department_id INT NOT NULL,
    current_job VARCHAR(100),
    FOREIGN KEY (department_id) REFERENCES departments(department_id)
);

CREATE TABLE classrooms (
    classroom_id INT AUTO_INCREMENT PRIMARY KEY,
    classroom_name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE classroom_resources (
    resource_id INT AUTO_INCREMENT PRIMARY KEY,
    classroom_id INT NOT NULL,
    resource_name VARCHAR(100) NOT NULL,
    resource_quantity INT NOT NULL,
    FOREIGN KEY (classroom_id) REFERENCES classrooms(classroom_id)
);
