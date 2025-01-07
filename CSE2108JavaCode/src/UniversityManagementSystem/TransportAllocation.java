package UniversityManagementSystem;

public class TransportAllocation {
    private int allocationId;
    private int transportId;
    private int userId;
    private String allocationDate;

    public TransportAllocation(int allocationId, int transportId, int userId, String allocationDate) {
        this.allocationId = allocationId;
        this.transportId = transportId;
        this.userId = userId;
        this.allocationDate = allocationDate;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(int allocationId) {
        this.allocationId = allocationId;
    }

    public int getTransportId() {
        return transportId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(String allocationDate) {
        this.allocationDate = allocationDate;
    }
}
