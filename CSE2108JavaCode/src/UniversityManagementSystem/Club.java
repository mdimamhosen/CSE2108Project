package UniversityManagementSystem;

public class Club {
    private int clubId;
    private String clubName;
    private String clubDescription;
    private String creationDate;
    private int headOfClub;

    public Club(int clubId, String clubName, String clubDescription, String creationDate, int headOfClub) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.creationDate = creationDate;
        this.headOfClub = headOfClub;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getHeadOfClub() {
        return headOfClub;
    }

    public void setHeadOfClub(int headOfClub) {
        this.headOfClub = headOfClub;
    }
}

