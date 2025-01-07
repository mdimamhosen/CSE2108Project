package UniversityManagementSystem;

public class Teacher {
    private int teacher_id;
    private int faculty_id;
    private int course_id;
    private String first_name;
    private String last_name;
    private String email;

    public Teacher(int teacher_id, int faculty_id, int course_id, String first_name, String last_name, String email) {
        this.teacher_id = teacher_id;
        this.faculty_id = faculty_id;
        this.course_id = course_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
