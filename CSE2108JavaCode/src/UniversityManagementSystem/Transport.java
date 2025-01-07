package UniversityManagementSystem;
public class Transport {
    private int transportId;
    private String vehicleType;
    private String routeName;
    private int capacity;
    private String driverName;
    private String contactNumber;

    public Transport(int transportId, String vehicleType, String routeName, int capacity, String driverName, String contactNumber) {
        this.transportId = transportId;
        this.vehicleType = vehicleType;
        this.routeName = routeName;
        this.capacity = capacity;
        this.driverName = driverName;
        this.contactNumber = contactNumber;
    }

    public int getTransportId() {
        return transportId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

