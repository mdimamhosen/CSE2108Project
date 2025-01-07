package UniversityManagementSystem;

public class ExamResult {
    private int result_id;
    private int exam_id;
    private int student_id;
    private int marks;
    private String grade;

    public ExamResult(int result_id, int exam_id, int student_id, int marks, String grade) {
        this.result_id = result_id;
        this.exam_id = exam_id;
        this.student_id = student_id;
        this.marks = marks;
        this.grade = grade;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
