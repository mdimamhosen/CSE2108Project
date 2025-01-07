package UniversityManagementSystem;

public class Alumni {
    private int alumniId;
    private String firstName;
    private String lastName;
    private String email;
    private int graduationYear;
    private int departmentId;
    private String currentJob;

    public Alumni(int alumniId, String firstName, String lastName, String email, int graduationYear, int departmentId, String currentJob) {
        this.alumniId = alumniId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.graduationYear = graduationYear;
        this.departmentId = departmentId;
        this.currentJob = currentJob;
    }

    public int getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(int alumniId) {
        this.alumniId = alumniId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }
}
