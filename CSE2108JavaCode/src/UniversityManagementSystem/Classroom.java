package UniversityManagementSystem;

public class Classroom {
    private int classroomId;
    private String classroomName;
    private String location;
    private int capacity;

    public Classroom(int classroomId, String classroomName, String location, int capacity) {
        this.classroomId = classroomId;
        this.classroomName = classroomName;
        this.location = location;
        this.capacity = capacity;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
