package UniversityManagementSystem;

import java.util.Date;

public class Examination {
    private int exam_id;
    private String exam_name;
    private Date exam_date;
    private int department_id;

    public Examination(int exam_id, String exam_name, Date exam_date, int department_id) {
        this.exam_id = exam_id;
        this.exam_name = exam_name;
        this.exam_date = exam_date;
        this.department_id = department_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public Date getExam_date() {
        return exam_date;
    }

    public void setExam_date(Date exam_date) {
        this.exam_date = exam_date;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }
}
