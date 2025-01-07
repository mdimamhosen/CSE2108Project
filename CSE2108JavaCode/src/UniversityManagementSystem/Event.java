package UniversityManagementSystem;

import java.util.Date;

public class Event {
    private int event_id;
    private String event_name;
    private Date event_date;
    private String description;

    public Event(int event_id, String event_name, Date event_date, String description) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.description = description;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
