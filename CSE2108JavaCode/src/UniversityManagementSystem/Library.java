package UniversityManagementSystem;

public class Library {
    private int libraryId;
    private String libraryName;
    private String location;

    public Library(int libraryId, String libraryName, String location) {
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.location = location;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
