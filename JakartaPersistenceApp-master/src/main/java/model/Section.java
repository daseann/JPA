package model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    //@Column(unique=true)
    public int sectionNumber;
    public int maxCapacity;

    @OneToOne
    public Semester semester;

    @OneToOne
    public TimeSlot timeSlot;

    @OneToOne
    public Course course;

    @OneToMany
    public List<Student> studentList = new ArrayList<>();


    public Section() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void addSemester(Semester s){
        this.semester = s;
        s.addSection(this);
    }

    public void addStudent(Student std){
        this.studentList.add(std);
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", sectionNumber=" + sectionNumber +
                ", maxCapacity=" + maxCapacity +
                ", semester=" + semester.toString() +
                ", timeSlot=" + timeSlot.toString() +
                ", course=" + course.toString() +
                ", studentList=" + studentList.toString() +
                '}';
    }
}
