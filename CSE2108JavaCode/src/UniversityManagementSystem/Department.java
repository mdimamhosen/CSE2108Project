package UniversityManagementSystem;

public class Department {
    private int department_id;
    private String department_name;
    private int head_of_department;
    private int floor;

    public Department(int department_id, String department_name, int head_of_department, int floor) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.head_of_department = head_of_department;
        this.floor = floor;
    }
    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getHead_of_department() {
        return head_of_department;
    }

    public void setHead_of_department(int head_of_department) {
        this.head_of_department = head_of_department;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
