package UniversityManagementSystem;

public class Course {
    private int course_id;
    private String course_name;
    private String course_code;
    private int department_id;
    private int credits;

    public Course(int course_id, String course_name, String course_code, int department_id, int credits) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_code = course_code;
        this.department_id = department_id;
        this.credits = credits;
    }
    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
