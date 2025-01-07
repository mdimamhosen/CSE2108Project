package UniversityManagementSystem;

public class Faculty {
    private int faculty_id;
    private int user_id;
    private int department_id;
    private String position;
    private String first_name;
    private String last_name;
    private String email;

    public Faculty(int faculty_id, int user_id, int department_id, String position, String first_name, String last_name, String email) {
        this.faculty_id = faculty_id;
        this.user_id = user_id;
        this.department_id = department_id;
        this.position = position;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
