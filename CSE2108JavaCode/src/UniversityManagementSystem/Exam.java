package UniversityManagementSystem;

public class Exam {
    private int exam_id;
    private int course_id;
    private String exam_name;
    private String exam_date;

    public Exam(int exam_id, int course_id, String exam_name, String exam_date) {
        this.exam_id = exam_id;
        this.course_id = course_id;
        this.exam_name = exam_name;
        this.exam_date = exam_date;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }
}
