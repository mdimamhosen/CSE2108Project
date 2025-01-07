package UniversityManagementSystem;

public class Hostel {
    private int hostelId;
    private String hostelName;
    private String location;
    private int capacity;

    public Hostel(int hostelId, String hostelName, String location, int capacity) {
        this.hostelId = hostelId;
        this.hostelName = hostelName;
        this.location = location;
        this.capacity = capacity;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
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
